package kg.attractor.edufood.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.dto.UserDto;
import kg.attractor.edufood.service.interfaces.CartService;
import kg.attractor.edufood.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("mvcCart")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @PostMapping
    public String addToCart(@RequestParam Long productId,
                            @RequestParam Integer quantity,
                            @RequestParam(required = false) String redirectUrl,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        cartService.addToCart(productId, quantity, request, response);
        if (redirectUrl != null && !redirectUrl.isBlank()) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/";
    }

    @GetMapping
    public String getCartPage() {
        return "profile/cart";
    }

    @PostMapping("/update")
    public String updateCartQuantity(@RequestParam Long productId,
                                     @RequestParam Integer quantityChange,
                                     @RequestParam(required = false) String redirectUrl,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        cartService.updateCartItemQuantity(productId, quantityChange, request, response);

        if (redirectUrl != null && !redirectUrl.isBlank()) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/cart";
    }

    @PostMapping("checkout")
    public String checkoutCart(HttpServletRequest request,
                               HttpServletResponse response,
                               RedirectAttributes redirectAttributes) {
        try {
            UserDto user = userService.getAuthUser();
            cartService.checkout(request, response, user);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Заказ успешно оформлен!");

            return "redirect:/profile/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Произошла ошибка при оформлении заказа: " + e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/merge-options")
    public String showMergeOptions(HttpServletRequest request,HttpServletResponse response, Model model) {
        if (cartService.hasGuestCart(request)) {
            OrderDto guestCart = cartService.getGuestCart(request);
            model.addAttribute("guestCart", guestCart);

            UserDto user = userService.getAuthUser();
            OrderDto userCart = cartService.getOrCreateUserCart(user, request, response);
            model.addAttribute("userCart", userCart);
            return "profile/cart-merge";
        }
        return "redirect:/cart";
    }

    @PostMapping("/merge")
    public String mergeCart(@RequestParam String action,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes) {
        UserDto user = userService.getAuthUser();

        try {
            if ("merge".equals(action)) {
                cartService.mergeGuestCartWithUserCart(user, request, response);
                redirectAttributes.addFlashAttribute("successMessage", "Корзины успешно объединены");
            } else if ("replace".equals(action)) {
                cartService.replaceUserCartWithGuestCart(user, request, response);
                redirectAttributes.addFlashAttribute("successMessage",
                        "Корзина пользователя заменена гостевой корзиной");
            }

            return "redirect:/cart";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Произошла ошибка при объединении корзин: " + e.getMessage());
            return "redirect:/cart";
        }
    }
}