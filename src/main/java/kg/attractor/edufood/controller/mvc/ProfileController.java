package kg.attractor.edufood.controller.mvc;

import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.dto.UserDto;
import kg.attractor.edufood.service.interfaces.OrderService;
import kg.attractor.edufood.service.interfaces.OrderStatusService;
import kg.attractor.edufood.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderStatusService orderStatusService;

    @GetMapping
    public String getProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDto user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("section", "profile");

        List<OrderDto> recentOrders = null;
        try {
            recentOrders = orderService.getRecentOrdersByUserId(user.getId());
        } catch (NoSuchElementException e) {
        }

        model.addAttribute("recentOrders", recentOrders);
        return "profile/profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute UserDto userDto,
            RedirectAttributes redirectAttributes) {

        try {
            UserDto user = userService.getAuthUser();
            userService.updateUser(user.getId(), userDto);
            redirectAttributes.addFlashAttribute("successMessage", "Профиль успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении профиля: " + e.getMessage());
        }
        return "redirect:/profile";
    }

    @PostMapping("/avatar")
    public String uploadAvatar(
            @RequestParam("avatar") MultipartFile avatarFile,
            RedirectAttributes redirectAttributes) {

        try {
            UserDto user = userService.getAuthUser();

            userService.uploadAvatar(avatarFile, user.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Аватар успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при загрузке аватара: " + e.getMessage());
        }

        return "redirect:/profile";
    }

    @GetMapping("/orders")
    public String getOrdersPage(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortBy", defaultValue = "date") String sortBy,
            @RequestParam(name = "dateFrom", required = false) LocalDate dateFrom,
            @RequestParam(name = "dateTo", required = false) LocalDate dateTo,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection,
            Model model) {


        UserDto user = userService.getAuthUser();
        model.addAttribute("user", user);
        model.addAttribute("section", "orders");

        Pageable pageable = orderService.createPageableWithSort(page, size, sortDirection, sortBy);
        Page<OrderDto> orders = null;

        try {
            orders = orderService.getFilteredOrders(
                    user.getId(), dateFrom, dateTo, pageable);

            model.addAttribute("allOrders", orders.getContent());
            model.addAttribute("totalPages", orders.getTotalPages());
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Заказы не были найдены, попробуйте поменять критерии поиска!");
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("orderStatuses", orderStatusService.getAllOrderStatuses());

        return "profile/orders";
    }

    @PreAuthorize("@orderService.isAuthorOfOrder(#orderId, authentication.principal.userId)")
    @GetMapping("/orders/{orderId}")
    public String getOrderDetails(
            @PathVariable Long orderId,
            Model model) {
        OrderDto order = orderService.getOrderDtoById(orderId);

        if (order.getStatus().getName().equals("Корзина")) {
            return "redirect:/cart";
        }

        model.addAttribute("order", order);
        return "profile/order";
    }

}