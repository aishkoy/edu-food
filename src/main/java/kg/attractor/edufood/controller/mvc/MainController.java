package kg.attractor.edufood.controller.mvc;

import kg.attractor.edufood.service.interfaces.ProductCategoryService;
import kg.attractor.edufood.service.interfaces.ProductService;
import kg.attractor.edufood.service.interfaces.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller("mvcMain")
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final ProductCategoryService productCategoryService;
    private final RestaurantService restaurantService;
    private final ProductService productService;

    @GetMapping
    public String getMainPage(Model model) {
        try{

            model.addAttribute("categories", productCategoryService.getAllCategories());
            model.addAttribute("restaurants", restaurantService.getMostOrderedRestaurants(6));
            model.addAttribute("products", productService.getMostOrderedProducts(8));
        }catch (NoSuchElementException ignored){}
        return "index";
    }
}
