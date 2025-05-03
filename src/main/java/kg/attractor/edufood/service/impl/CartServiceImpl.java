package kg.attractor.edufood.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edufood.dto.*;
import kg.attractor.edufood.entity.Order;
import kg.attractor.edufood.service.interfaces.*;
import kg.attractor.edufood.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private static final String GUEST_CART_COOKIE = "guest_cart";
    private static final String USER_CART_COOKIE_PREFIX = "user_cart_";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30;
    private static final String COOKIE_PATH = "/";

    private final ProductService productService;
    private final OrderStatusService orderStatusService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final UserService userService;
    private final CookieUtils cookieUtils;

    @Override
    public void addToCart(Long productId, Integer quantity, HttpServletRequest request, HttpServletResponse response) {
        OrderDto cart = getOrCreateCart(request, response);
        ProductDto productDto = productService.getProductById(productId);
        validateQuantity(quantity);

        addOrUpdateCartItem(cart, productDto, quantity);
        updateCart(cart, response);

        log.info("Продукт успешно добавлен в корзину. Текущее состояние корзины: {}", cart);
    }

    @Override
    public OrderDto getOrCreateCart(HttpServletRequest request, HttpServletResponse response) {
        UserDto user = userService.getAuthUser();

        if (user != null) {
            return getOrCreateUserCart(user, request, response);
        } else {
            return getOrCreateGuestCart(request, response);
        }
    }

    @Override
    public OrderDto getOrCreateUserCart(UserDto user, HttpServletRequest request, HttpServletResponse response) {
        String userCartCookie = USER_CART_COOKIE_PREFIX + user.getId();
        Optional<OrderDto> userCart = cookieUtils.getCookieValue(request, userCartCookie, OrderDto.class);

        if (userCart.isPresent()) {
            return userCart.get();
        } else {
            OrderDto newCart = createNewCart();
            newCart.setUser(user);
            cookieUtils.setCookieValue(response, userCartCookie, newCart, COOKIE_MAX_AGE, COOKIE_PATH);
            return newCart;
        }
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
    public OrderDto updateCartItemQuantity(Long productId, Integer quantityChange,
                                           HttpServletRequest request, HttpServletResponse response) {
        OrderDto cart = getOrCreateCart(request, response);
        OrderProductDto cartItem = findExistingCartItem(cart, productId);

        if (cartItem == null) {
            return cart;
        }

        int newQuantity = cartItem.getQuantity() + quantityChange;

        if (newQuantity <= 0) {
            log.info("Удаление продукта с ID {} из корзины, т.к. новое количество <= 0", productId);
            Iterator<OrderProductDto> iterator = cart.getOrderProducts().iterator();
            while (iterator.hasNext()) {
                OrderProductDto item = iterator.next();
                if (item.getProduct().getId().equals(productId)) {
                    iterator.remove();
                    break;
                }
            }
        } else {
            cartItem.setQuantity(newQuantity);
            cartItem.setAmount(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            log.info("Обновлено количество для продукта: productId={}, new quantity={}, amount={}",
                    productId, newQuantity, cartItem.getAmount());
        }

        recalculateCart(cart);
        updateCart(cart, response);
        return cart;
    }

    @Transactional
    @Override
    public void checkout(HttpServletRequest request, HttpServletResponse response, UserDto user) {
        OrderDto cartDto = getOrCreateCart(request, response);

        if (cartDto.getOrderProducts().isEmpty()) {
            throw new IllegalStateException("Корзина пуста. Невозможно оформить заказ");
        }

        OrderStatusDto status = orderStatusService.getOrderStatusByName("Оплачен");
        OrderDto order = OrderDto.builder()
                .totalAmount(cartDto.getTotalAmount())
                .totalQuantity(cartDto.getTotalQuantity())
                .status(status)
                .user(user)
                .build();

        Order savedOrder = orderService.save(order);

        for (OrderProductDto cartItemDto : cartDto.getOrderProducts()) {
            OrderProductDto orderProduct = OrderProductDto.builder()
                    .orderId(savedOrder.getId())
                    .product(cartItemDto.getProduct())
                    .amount(cartItemDto.getAmount())
                    .quantity(cartItemDto.getQuantity())
                    .build();

            orderProductService.save(orderProduct);
        }

        clearCart(user, response);
        log.info("Корзина очищена после оформления заказа");
    }

    @Override
    public void mergeGuestCartWithUserCart(UserDto user, HttpServletRequest request, HttpServletResponse response) {
        Optional<OrderDto> guestCartOpt = cookieUtils.getCookieValue(request, GUEST_CART_COOKIE, OrderDto.class);
        String userCartCookie = USER_CART_COOKIE_PREFIX + user.getId();
        OrderDto userCart = getOrCreateUserCart(user, request, response);

        if (guestCartOpt.isPresent()) {
            OrderDto guestCart = guestCartOpt.get();


            for (OrderProductDto guestItem : guestCart.getOrderProducts()) {
                OrderProductDto existingItem = findExistingCartItem(userCart, guestItem.getProduct().getId());

                if (existingItem != null) {
                    existingItem.setQuantity(existingItem.getQuantity() + guestItem.getQuantity());
                    existingItem.setAmount(existingItem.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(existingItem.getQuantity())));
                } else {
                    userCart.getOrderProducts().add(guestItem);
                }
            }

            recalculateCart(userCart);

            cookieUtils.setCookieValue(response, userCartCookie, userCart, COOKIE_MAX_AGE, COOKIE_PATH);
            log.info("Корзины успешно объединены");
        }
    }

    @Override
    public void replaceUserCartWithGuestCart(UserDto user, HttpServletRequest request, HttpServletResponse response) {
        Optional<OrderDto> guestCartOpt = cookieUtils.getCookieValue(request, GUEST_CART_COOKIE, OrderDto.class);

        if (guestCartOpt.isPresent()) {
            OrderDto guestCart = guestCartOpt.get();
            String userCartCookie = USER_CART_COOKIE_PREFIX + user.getId();


            clearCart(user, response);
            guestCart.setUser(user);

            cookieUtils.setCookieValue(response, userCartCookie, guestCart, COOKIE_MAX_AGE, COOKIE_PATH);
            log.info("Корзина пользователя заменена на гостевую корзину");
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

    @Override
    public void clearGuestCart(HttpServletResponse response) {
        cookieUtils.deleteCookie(response, GUEST_CART_COOKIE, COOKIE_PATH);
    }

    private OrderDto createNewCart() {
        OrderStatusDto status = orderStatusService.getOrderStatusByName("Корзина");
        return OrderDto.builder()
                .status(status)
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

    private void updateCart(OrderDto cart, HttpServletResponse response) {
        UserDto user = userService.getAuthUser();

        if (user != null) {
            String userCartCookie = USER_CART_COOKIE_PREFIX + user.getId();
            cookieUtils.setCookieValue(response, userCartCookie, cart, COOKIE_MAX_AGE, COOKIE_PATH);
        } else {
            cookieUtils.setCookieValue(response, GUEST_CART_COOKIE, cart, COOKIE_MAX_AGE, COOKIE_PATH);
        }
    }

    private void clearCart(UserDto user, HttpServletResponse response) {
        if (user != null) {
            cookieUtils.deleteCookie(response, USER_CART_COOKIE_PREFIX + user.getId(), COOKIE_PATH);
        } else {
            cookieUtils.deleteCookie(response, GUEST_CART_COOKIE, COOKIE_PATH);
        }
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