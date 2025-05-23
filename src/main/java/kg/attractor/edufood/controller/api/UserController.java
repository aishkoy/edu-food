package kg.attractor.edufood.controller.api;

import kg.attractor.edufood.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}/image")
    public ResponseEntity<?> getImage(@PathVariable("id") Long userId) {
        return userService.getUserAvatar(userId);
    }
}
