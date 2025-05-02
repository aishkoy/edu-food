package kg.attractor.edufood.controller.mvc;

import kg.attractor.edufood.dto.ProductDto;
import kg.attractor.edufood.service.interfaces.ProductCategoryService;
import kg.attractor.edufood.service.interfaces.ProductService;
import kg.attractor.edufood.service.interfaces.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller("mvcProduct")
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final RestaurantService restaurantService;

    @GetMapping
    public String getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "name") String sortBy,
            Model model) {

        Pageable pageable = productService.createPageableWithSort(page, size, sortDirection, sortBy);

        Page<ProductDto> productsPage = productService.getFilteredProducts(
                name, minPrice, maxPrice, categoryId, restaurantId, pageable);

        model.addAttribute("products", productsPage);
        model.addAttribute("categories", productCategoryService.getAllCategories());
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        model.addAttribute("name", name);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "product/list";
    }
}