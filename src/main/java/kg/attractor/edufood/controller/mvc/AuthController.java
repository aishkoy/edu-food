package kg.attractor.edufood.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.edufood.dto.UserDto;
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
}
