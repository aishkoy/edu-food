package kg.attractor.edufood.controller.mvc;

import jakarta.servlet.http.HttpSession;
import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.service.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("mvcCart")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public String addToCart(@RequestParam Long productId,
                            @RequestParam Integer quantity,
                            @RequestParam(required = false) String redirectUrl,
                            HttpSession session) {
        cartService.addToCart(productId, quantity, session);
        if (redirectUrl != null && !redirectUrl.isBlank()) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/";
    }


    @GetMapping
    public String getCartPage(HttpSession session, Model model) {
        OrderDto sessionCart = (OrderDto) session.getAttribute("cart");
        if (sessionCart == null) {
            cartService.getOrCreateCart(session);
        }

        return "profile/cart";
    }
}