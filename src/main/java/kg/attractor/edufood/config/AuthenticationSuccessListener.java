package kg.attractor.edufood.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edufood.dto.UserDto;
import kg.attractor.edufood.service.interfaces.CartService;
import kg.attractor.edufood.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    private final CartService cartService;
    private final UserService userService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        HttpServletResponse response = attrs.getResponse();

        if (cartService.hasGuestCart(request)) {
            request.getSession().setAttribute("showCartMergeOptions", true);
        } else {
            UserDto user = userService.getAuthUser();
            if (user != null) {
                cartService.getOrCreateUserCart(user, request, response);
            }
        }
    }
}