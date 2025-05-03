package kg.attractor.edufood.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edufood.dto.*;
import kg.attractor.edufood.entity.Order;
import kg.attractor.edufood.entity.OrderProduct;
import kg.attractor.edufood.entity.OrderStatus;
import kg.attractor.edufood.service.interfaces.*;
import kg.attractor.edufood.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private static final String GUEST_CART_COOKIE = "guest_cart";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30;
    private static final String COOKIE_PATH = "/";

    private final ProductService productService;
    private final OrderStatusService orderStatusService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final UserService userService;
    private final CookieUtils cookieUtils;

    @Override
    @Transactional
    public void addToCart(Long productId, Integer quantity, HttpServletRequest request, HttpServletResponse response) {
        UserDto user = userService.getAuthUser();
        ProductDto productDto = productService.getProductById(productId);
        validateQuantity(quantity);

        if (user != null) {
            addToCartInDb(user, productDto, quantity);
        } else {
            OrderDto guestCart = getOrCreateGuestCart(request, response);
            addOrUpdateCartItem(guestCart, productDto, quantity);
            updateGuestCart(guestCart, response);
        }

        log.info("Продукт успешно добавлен в корзину.");
    }

    @Transactional
    public void addToCartInDb(UserDto user, ProductDto productDto, Integer quantity) {
        Order cart = orderService.getOrCreateDbCart(user);

        Optional<OrderProduct> existingProductOpt = orderProductService.getByOrderIdAndProductId(
                cart.getId(), productDto.getId());

        if (existingProductOpt.isPresent()) {
            OrderProduct existingProduct = existingProductOpt.get();
            int newQuantity = existingProduct.getQuantity() + quantity;
            BigDecimal newAmount = productDto.getPrice().multiply(BigDecimal.valueOf(newQuantity));

            existingProduct.setQuantity(newQuantity);
            existingProduct.setAmount(newAmount);
            orderProductService.saveEntity(existingProduct);
        } else {
            OrderProductDto newProduct = OrderProductDto.builder()
                    .orderId(cart.getId())
                    .product(productDto)
                    .quantity(quantity)
                    .amount(productDto.getPrice().multiply(BigDecimal.valueOf(quantity)))
                    .build();

            orderProductService.save(newProduct);
        }

        recalculateDbCart(cart.getId());

        log.info("Продукт успешно добавлен в корзину пользователя в БД: userId={}, productId={}",
                user.getId(), productDto.getId());
    }



    @Override
    public OrderDto getOrCreateCart(HttpServletRequest request, HttpServletResponse response) {
        UserDto user = userService.getAuthUser();

        if (user != null) {
            Order dbCart = orderService.getOrCreateDbCart(user);
            return orderService.mapToDto(dbCart);
        } else {
            return getOrCreateGuestCart(request, response);
        }
    }

    @Override
    public OrderDto getOrCreateUserCart(UserDto user, HttpServletRequest request, HttpServletResponse response) {
        Order dbCart = orderService.getOrCreateDbCart(user);
        return orderService.mapToDto(dbCart);
    }

    private OrderDto getOrCreateGuestCart(HttpServletRequest request, HttpServletResponse response) {
        log.info("Проверка существования гостевой корзины в куках.");
        Optional<OrderDto> guestCart = cookieUtils.getCookieValue(request, GUEST_CART_COOKIE, OrderDto.class);

        if (guestCart.isPresent()) {
            return guestCart.get();
        } else {
            OrderDto newCart = createNewCart();
            cookieUtils.setCookieValue(response, GUEST_CART_COOKIE, newCart, COOKIE_MAX_AGE, COOKIE_PATH);
            return newCart;
        }
    }

    @Override
    @Transactional
    public OrderDto updateCartItemQuantity(Long productId, Integer quantityChange,
                                           HttpServletRequest request, HttpServletResponse response) {
        UserDto user = userService.getAuthUser();

        if (user != null) {
            return updateCartItemQuantityInDb(user, productId, quantityChange);
        } else {
            OrderDto guestCart = getOrCreateGuestCart(request, response);
            OrderProductDto cartItem = findExistingCartItem(guestCart, productId);

            if (cartItem == null) {
                return guestCart;
            }

            int newQuantity = cartItem.getQuantity() + quantityChange;

            if (newQuantity <= 0) {
                log.info("Удаление продукта с ID {} из корзины, т.к. новое количество <= 0", productId);
                guestCart.getOrderProducts().removeIf(item -> item.getProduct().getId().equals(productId));
            } else {
                cartItem.setQuantity(newQuantity);
                cartItem.setAmount(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
                log.info("Обновлено количество для продукта: productId={}, new quantity={}, amount={}",
                        productId, newQuantity, cartItem.getAmount());
            }

            recalculateCart(guestCart);
            updateGuestCart(guestCart, response);
            return guestCart;
        }
    }

    @Transactional
    public OrderDto updateCartItemQuantityInDb(UserDto user, Long productId, Integer quantityChange) {
        Order dbCart = orderService.getOrCreateDbCart(user);
        Optional<OrderProduct> productOpt = orderProductService.getByOrderIdAndProductId(
                dbCart.getId(), productId);

        if (productOpt.isPresent()) {
            OrderProduct orderProduct = productOpt.get();
            int newQuantity = orderProduct.getQuantity() + quantityChange;

            if (newQuantity <= 0) {
                orderProductService.deleteByOrderAndProduct(orderProduct.getOrder().getId(), orderProduct.getProduct().getId());
            } else {
                BigDecimal newAmount = orderProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity));
                orderProduct.setQuantity(newQuantity);
                orderProduct.setAmount(newAmount);
                orderProductService.saveEntity(orderProduct);
            }

            recalculateDbCart(dbCart.getId());
        }

        return orderService.getOrderDtoById(dbCart.getId());
    }

    @Transactional
    @Override
    public void checkout(HttpServletRequest request, HttpServletResponse response, UserDto user) {
        OrderStatusDto cartStatus = orderStatusService.getOrderStatusDtoByName("Корзина");
        Optional<Order> cartOpt = orderService.getByUserIdAndStatusId(user.getId(), cartStatus.getId());

        if (cartOpt.isEmpty()) {
            throw new IllegalStateException("Корзина пуста. Невозможно оформить заказ");
        }

        Order cart = cartOpt.get();
        List<OrderProductDto> orderProducts = orderProductService.getAllByOrderId(cart.getId());

        if (orderProducts.isEmpty()) {
            throw new IllegalStateException("Корзина пуста. Невозможно оформить заказ");
        }

        OrderStatus paidStatus = orderStatusService.getOrderStatusByName("Оплачен");
        cart.setStatus(paidStatus);
        orderService.saveEntity(cart);

        OrderDto newCart = OrderDto.builder()
                .status(cartStatus)
                .user(user)
                .totalAmount(BigDecimal.ZERO)
                .totalQuantity(0)
                .build();

        orderService.save(newCart);
        log.info("Заказ успешно оформлен");
    }

    @Override
    @Transactional
    public void mergeGuestCartWithUserCart(UserDto user, HttpServletRequest request, HttpServletResponse response) {
        Optional<OrderDto> guestCartOpt = cookieUtils.getCookieValue(request, GUEST_CART_COOKIE, OrderDto.class);

        if (guestCartOpt.isPresent() && !guestCartOpt.get().getOrderProducts().isEmpty()) {
            OrderDto guestCart = guestCartOpt.get();
            Order userDbCart = orderService.getOrCreateDbCart(user);

            for (OrderProductDto guestItem : guestCart.getOrderProducts()) {
                Optional<OrderProduct> existingProductOpt = orderProductService.getByOrderIdAndProductId(
                        userDbCart.getId(), guestItem.getProduct().getId());

                if (existingProductOpt.isPresent()) {
                    OrderProduct existingProduct = existingProductOpt.get();
                    int newQuantity = existingProduct.getQuantity() + guestItem.getQuantity();
                    BigDecimal newAmount = existingProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity));

                    existingProduct.setQuantity(newQuantity);
                    existingProduct.setAmount(newAmount);
                    orderProductService.saveEntity(existingProduct);
                } else {
                    OrderProductDto newItem = OrderProductDto.builder()
                            .orderId(userDbCart.getId())
                            .product(guestItem.getProduct())
                            .quantity(guestItem.getQuantity())
                            .amount(guestItem.getAmount())
                            .build();

                    orderProductService.save(newItem);
                }
            }

            recalculateDbCart(userDbCart.getId());


            log.info("Корзины успешно объединены");
        }
    }

    @Override
    @Transactional
    public void replaceUserCartWithGuestCart(UserDto user, HttpServletRequest request, HttpServletResponse response) {
        Optional<OrderDto> guestCartOpt = cookieUtils.getCookieValue(request, GUEST_CART_COOKIE, OrderDto.class);

        if (guestCartOpt.isPresent() && !guestCartOpt.get().getOrderProducts().isEmpty()) {
            OrderDto guestCart = guestCartOpt.get();

            try {
                OrderStatusDto cartStatus = orderStatusService.getOrderStatusDtoByName("Корзина");

                Optional<Order> existingCartOpt = orderService.getByUserIdAndStatusId(user.getId(), cartStatus.getId());

                existingCartOpt.ifPresent(orderService::delete);

                OrderDto newCartDto = OrderDto.builder()
                        .status(cartStatus)
                        .user(user)
                        .totalAmount(BigDecimal.ZERO)
                        .totalQuantity(0)
                        .build();

                Order newCart = orderService.save(newCartDto);

                for (OrderProductDto guestItem : guestCart.getOrderProducts()) {
                    OrderProductDto newItem = OrderProductDto.builder()
                            .orderId(newCart.getId())
                            .product(guestItem.getProduct())
                            .quantity(guestItem.getQuantity())
                            .amount(guestItem.getAmount())
                            .build();

                    orderProductService.save(newItem);
                }

                recalculateDbCart(newCart.getId());

                log.info("Создана новая корзина пользователя из гостевой корзины, ID новой корзины: {}", newCart.getId());
            } catch (Exception e) {
                log.error("Ошибка при замене корзины пользователя: {}", e.getMessage(), e);
                throw e;
            }
        }
    }
    @Override
    public boolean hasGuestCart(HttpServletRequest request) {
        Optional<OrderDto> guestCart = cookieUtils.getCookieValue(request, GUEST_CART_COOKIE, OrderDto.class);
        return guestCart.isPresent() && !guestCart.get().getOrderProducts().isEmpty();
    }

    @Override
    public OrderDto getGuestCart(HttpServletRequest request) {
        return cookieUtils.getCookieValue(request, GUEST_CART_COOKIE, OrderDto.class)
                .orElse(createNewCart());
    }

    private OrderDto createNewCart() {
        OrderStatusDto status = orderStatusService.getOrderStatusDtoByName("Корзина");
        return OrderDto.builder()
                .status(status)
                .totalAmount(BigDecimal.ZERO)
                .totalQuantity(0)
                .build();
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть больше 0.");
        }
        log.info("Количество валидно: {}", quantity);
    }

    private void addOrUpdateCartItem(OrderDto cart, ProductDto productDto, Integer quantity) {
        OrderProductDto existingItem = findExistingCartItem(cart, productDto.getId());

        if (existingItem != null) {
            updateCartItem(existingItem, productDto, quantity);
        } else {
            addNewCartItem(cart, productDto, quantity);
        }

        recalculateCart(cart);
    }

    private OrderProductDto findExistingCartItem(OrderDto cart, Long productId) {
        log.info("Поиск продукта в корзине по id {}", productId);
        return cart.getOrderProducts().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    private void updateCartItem(OrderProductDto existingItem, ProductDto productDto, Integer quantity) {
        existingItem.setQuantity(existingItem.getQuantity() + quantity);
        BigDecimal amount = productDto.getPrice().multiply(BigDecimal.valueOf(existingItem.getQuantity()));
        existingItem.setAmount(amount);
        log.info("Обновлено количество и сумма для продукта: productId={}, quantity={}, amount={}",
                productDto.getId(), existingItem.getQuantity(), existingItem.getAmount());
    }

    private void addNewCartItem(OrderDto cart, ProductDto productDto, Integer quantity) {
        OrderProductDto newItem = OrderProductDto.builder()
                .product(productDto)
                .quantity(quantity)
                .amount(productDto.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .build();
        cart.getOrderProducts().add(newItem);
        log.info("Добавлен новый продукт в корзину: {}", newItem);
    }

    private void updateGuestCart(OrderDto cart, HttpServletResponse response) {
        cookieUtils.setCookieValue(response, GUEST_CART_COOKIE, cart, COOKIE_MAX_AGE, COOKIE_PATH);
    }

    @Transactional
    public void recalculateDbCart(Long orderId) {
        BigDecimal totalAmount = orderProductService.calculateTotalAmountByOrderId(orderId);
        int totalQuantity = orderProductService.calculateTotalQuantityByOrderId(orderId);

        orderService.updateTotals(orderId, totalAmount, totalQuantity);
        log.info("Корзина в БД пересчитана: orderId={}, totalAmount={}, totalQuantity={}",
                orderId, totalAmount, totalQuantity);
    }

    private void recalculateCart(OrderDto cart) {
        BigDecimal totalPrice = calculateTotalPrice(cart);
        int totalQuantity = calculateTotalQuantity(cart);

        cart.setTotalAmount(totalPrice);
        cart.setTotalQuantity(totalQuantity);

        log.info("Общая стоимость пересчитана: {}, Общее количество пересчитано: {}", totalPrice, totalQuantity);
    }

    private BigDecimal calculateTotalPrice(OrderDto cart) {
        return cart.getOrderProducts().stream()
                .map(OrderProductDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int calculateTotalQuantity(OrderDto cart) {
        return cart.getOrderProducts().stream()
                .map(OrderProductDto::getQuantity)
                .reduce(0, Integer::sum);
    }
}