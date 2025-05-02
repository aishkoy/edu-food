package kg.attractor.edufood.service.interfaces;

import jakarta.servlet.http.HttpSession;
import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.dto.UserDto;

public interface CartService {
    void addToCart(Long productId, Integer quantity, HttpSession session);

    OrderDto getOrCreateCart(HttpSession session);

    OrderDto updateCartItemQuantity(Long productId, Integer quantityChange, HttpSession session);

    void checkout(HttpSession session, UserDto user);
}
