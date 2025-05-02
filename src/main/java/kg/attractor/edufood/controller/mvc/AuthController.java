package kg.attractor.edufood.controller.mvc;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.edufood.dto.UserDto;
import kg.attractor.edufood.exception.iae.InvalidPasswordException;
import kg.attractor.edufood.exception.nsee.UserNotFoundException;
import kg.attractor.edufood.service.interfaces.RoleService;
import kg.attractor.edufood.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller("mvcAuth")
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("role", roleService.getRoleByName("user"));
        return "auth/register";
    }

    @PostMapping("register")
    public String login(@ModelAttribute("userDto") @Valid UserDto userDto,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("role", roleService.getRoleByName("user"));
            return "auth/register";
        }

        userService.register(userDto);
        return "redirect:/auth/login";
    }

    @GetMapping("login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "auth/login";
    }
//
//    @GetMapping("forgot-password")
//    public String showForgotPasswordPage() {
//        return "auth/forgot-password";
//    }
//
//    @PostMapping("forgot-password")
//    public String forgotPassword(HttpServletRequest req, Model model) {
//        try{
//            userService.makeResetPasswordLink(req);
//            model.addAttribute("message", "Ссылка для сброса пароля была отправлена на ваш email");
//        } catch (UserNotFoundException | IOException | IllegalArgumentException e){
//            model.addAttribute("error", e.getMessage());
//        } catch (MessagingException e) {
//            model.addAttribute("error", "Произошла ошибка при отправке письма");
//        }
//        return "auth/forgot-password";
//    }
//
//    @GetMapping("reset-password")
//    public String showResetPasswordPage(@RequestParam String token,
//                                        Model model) {
//        try{
//            userService.getUserByResetPasswordToken(token);
//            model.addAttribute("token", token);
//        } catch (UserNotFoundException ex) {
//            model.addAttribute("error", "Неверный токен!");
//        }
//        return "auth/reset-password";
//    }
//
//    @PostMapping("/reset-password")
//    public String processResetPassword(HttpServletRequest request, Model model) {
//        String token = request.getParameter("token");
//        String password = request.getParameter("password");
//        try {
//            UserDto user = userService.getUserByResetPasswordToken(token);
//            userService.updatePassword(user, password);
//            model.addAttribute("message", "Пароль успешно сброшен!");
//        } catch (UserNotFoundException ex) {
//            model.addAttribute("error", "Неверный токен!");
//            return "auth/reset-password";
//        } catch (InvalidPasswordException ex) {
//            model.addAttribute("error", ex.getMessage());
//            model.addAttribute("token", token);
//            return "auth/reset-password";
//        }
//        return "auth/message";
//    }

}
