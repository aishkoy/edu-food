package kg.attractor.edufood.service.interfaces;

import jakarta.servlet.http.HttpSession;
import kg.attractor.edufood.dto.OrderDto;

public interface CartService {
    void addToCart(Long productId, Integer quantity, HttpSession session);

    OrderDto getOrCreateCart(HttpSession session);
}
