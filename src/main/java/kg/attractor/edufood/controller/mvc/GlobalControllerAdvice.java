package kg.attractor.edufood.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.service.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    
    private final CartService cartService;
    
    @ModelAttribute("cart")
    public OrderDto getCart(HttpServletRequest request, HttpServletResponse response) {
        return cartService.getOrCreateCart(request, response);
    }
}