package kg.attractor.edufood.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.dto.UserDto;
import kg.attractor.edufood.service.interfaces.UserService;
import kg.attractor.edufood.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final String USER_CART_COOKIE_PREFIX = "user_cart_";
    private static final String COOKIE_PATH = "/";
    
    private final CookieUtils cookieUtils;
    private final UserService userService;
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, 
                               Authentication authentication) {
        if (authentication != null) {
            try {
                UserDto user = userService.getAuthUser();
                
                if (user != null) {
                    String userCartCookie = USER_CART_COOKIE_PREFIX + user.getId();
                    Optional<OrderDto> userCartOpt = cookieUtils.getCookieValue(request, userCartCookie, OrderDto.class);
                    
                    if (userCartOpt.isPresent()) {
                        OrderDto userCart = userCartOpt.get();
                        
                        if (userCart.getOrderProducts() == null || userCart.getOrderProducts().isEmpty()) {
                            log.info("Удаление пустой корзины пользователя с ID {} при выходе из системы", user.getId());
                            cookieUtils.deleteCookie(response, userCartCookie, COOKIE_PATH);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Ошибка при обработке выхода из системы: {}", e.getMessage());
            }
        }
        
        try {
            response.sendRedirect("/");
        } catch (Exception e) {
            log.error("Ошибка при перенаправлении после выхода: {}", e.getMessage());
        }
    }
}