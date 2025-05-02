package kg.attractor.edufood.controller.mvc;

import kg.attractor.edufood.dto.ProductDto;
import kg.attractor.edufood.dto.RestaurantDto;
import kg.attractor.edufood.service.interfaces.ProductService;
import kg.attractor.edufood.service.interfaces.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller("mvcRestaurant")
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final ProductService productService;

    @GetMapping
    public String getRestaurantsPage(@RequestParam(required = false) String name,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "asc") String sortDirection,
                                     @RequestParam(defaultValue = "name") String sortBy,
                                     Model model) {
        Pageable pageable = restaurantService.createPageableWithSort(page, size, sortDirection, sortBy);
        Page<RestaurantDto> restaurants = null;
        try {
            restaurants = restaurantService.getRestaurants(name, pageable);
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Рестораны не были найдены!");
        }

        model.addAttribute("restaurants", restaurants);
        model.addAttribute("name", name);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "restaurant/list";
    }

    @GetMapping("/{id}")
    public String getRestaurantDetailPage(@PathVariable Long id,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "9") int size,
                                          @RequestParam(defaultValue = "asc") String sortDirection,
                                          @RequestParam(defaultValue = "name") String sortBy,
                                          Model model) {

        RestaurantDto restaurant = null;
        try {
            restaurant = restaurantService.getRestaurantById(id);
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Ресторан не найден!");
        }
        model.addAttribute("restaurant", restaurant);

        Pageable pageable = productService.createPageableWithSort(page, size, sortDirection, sortBy);
        Page<ProductDto> products = productService.getAllRestaurantsProducts(id, pageable);
        model.addAttribute("products", products);

        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "restaurant/detail";

    }
}
