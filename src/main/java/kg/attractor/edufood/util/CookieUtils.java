package kg.attractor.edufood.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtils {
    private final ObjectMapper objectMapper;
    
    public <T> Optional<T> getCookieValue(HttpServletRequest request, String cookieName, Class<T> valueType) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst()
                .map(cookie -> {
                    try {
                        String decodedValue = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                        return objectMapper.readValue(decodedValue, valueType);
                    } catch (IOException e) {
                        log.error("Ошибка при чтении данных из куки {}: {}", cookieName, e.getMessage());
                        return null;
                    }
                });
    }
    
    public <T> void setCookieValue(HttpServletResponse response, String cookieName, T value, 
                                 int maxAgeSeconds, String path) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            String encodedValue = URLEncoder.encode(jsonValue, StandardCharsets.UTF_8);
            
            Cookie cookie = new Cookie(cookieName, encodedValue);
            cookie.setMaxAge(maxAgeSeconds);
            cookie.setPath(path);
            cookie.setHttpOnly(true);
            
            response.addCookie(cookie);
            log.info("Куки {} успешно установлены", cookieName);
        } catch (JsonProcessingException e) {
            log.error("Ошибка при сохранении данных в куки {}: {}", cookieName, e.getMessage());
        }
    }
}