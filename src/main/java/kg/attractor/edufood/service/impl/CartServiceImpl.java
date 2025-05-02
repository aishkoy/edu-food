package kg.attractor.edufood.service.impl;

import jakarta.servlet.http.HttpSession;
import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.dto.OrderProductDto;
import kg.attractor.edufood.dto.OrderStatusDto;
import kg.attractor.edufood.dto.ProductDto;
import kg.attractor.edufood.service.interfaces.CartService;
import kg.attractor.edufood.service.interfaces.OrderStatusService;
import kg.attractor.edufood.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductService productService;
    private final OrderStatusService orderStatusService;

    @Override
    public void addToCart(Long productId, Integer quantity, HttpSession session) {
        OrderDto cart = getOrCreateCart(session);
        ProductDto productDto = productService.getProductById(productId);
        validateQuantity(quantity);

        addOrUpdateCartItem(cart, productDto, quantity);
        updateSessionCart(session, cart);

        log.info("Продукт успешно добавлен в корзину. Текущее состояние корзины: {}", cart);
    }

    @Override
    public OrderDto getOrCreateCart(HttpSession session) {
        log.info("Проверка существования корзины в сессии.");
        return  session.getAttribute("cart") != null
                ? (OrderDto) session.getAttribute("cart")
                : createNewCart(session);
    }

    @Override
    public OrderDto updateCartItemQuantity(Long productId, Integer quantityChange, HttpSession session) {
        OrderDto cart = getOrCreateCart(session);
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
        updateSessionCart(session, cart);
        return cart;
    }

    private OrderDto createNewCart(HttpSession session) {
        OrderStatusDto status = orderStatusService.getOrderStatusByName("Корзина");
        OrderDto newCart = OrderDto.builder()
                .status(status)
                .build();
        session.setAttribute("cart", newCart);
        return newCart;
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

    private void updateSessionCart(HttpSession session, OrderDto cart) {
        session.setAttribute("cart", cart);
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