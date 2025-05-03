package kg.attractor.edufood.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

public interface CartService {
    void addToCart(Long productId, Integer quantity, HttpServletRequest request, HttpServletResponse response);

    OrderDto getOrCreateCart(HttpServletRequest request, HttpServletResponse response);

    OrderDto getOrCreateUserCart(UserDto user, HttpServletRequest request, HttpServletResponse response);

    OrderDto updateCartItemQuantity(Long productId, Integer quantityChange,
                                    HttpServletRequest request, HttpServletResponse response);

    @Transactional
    void checkout(HttpServletRequest request, HttpServletResponse response, UserDto user);

    void mergeGuestCartWithUserCart(UserDto user, HttpServletRequest request, HttpServletResponse response);

    void replaceUserCartWithGuestCart(UserDto user, HttpServletRequest request, HttpServletResponse response);

    boolean hasGuestCart(HttpServletRequest request);

    OrderDto getGuestCart(HttpServletRequest request);
}
