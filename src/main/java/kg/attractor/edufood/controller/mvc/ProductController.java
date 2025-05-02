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

@Controller
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
            @RequestParam(defaultValue = "12") int size,
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

        return "products/list";
    }

    @GetMapping("/categories/{categoryId}")
    public String getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "name") String sortBy,
            Model model) {

        Pageable pageable = productService.createPageableWithSort(page, size, sortDirection, sortBy);
        Page<ProductDto> productsPage = productService.getProductsByCategoryId(categoryId, pageable);

        model.addAttribute("products", productsPage);
        model.addAttribute("categories", productCategoryService.getAllCategories());
        model.addAttribute("currentCategory", productCategoryService.getProductCategoryById(categoryId));
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "products/category";
    }

    @GetMapping("/restaurants/{restaurantId}")
    public String getProductsByRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "name") String sortBy,
            Model model) {

        Pageable pageable = productService.createPageableWithSort(page, size, sortDirection, sortBy);
        Page<ProductDto> productsPage = productService.getAllRestaurantsProducts(restaurantId, pageable);

        model.addAttribute("products", productsPage);
        model.addAttribute("categories", productCategoryService.getAllCategories());
        model.addAttribute("currentRestaurant", restaurantService.getRestaurantById(restaurantId));
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "products/restaurant";
    }

    @GetMapping("/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        ProductDto product = productService.getProductById(id);
        model.addAttribute("product", product);

        Pageable pageable = productService.createPageableWithSort(1, 4, "asc", "name");
        Page<ProductDto> similarProducts = productService.getProductsByCategoryId(product.getCategory().getId(), pageable);
        model.addAttribute("similarProducts", similarProducts);

        return "products/details";
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {

        Pageable pageable = productService.createPageableWithSort(page, size, "asc", "name");
        Page<ProductDto> searchResults = productService.getProductsByName(query, pageable);

        model.addAttribute("products", searchResults);
        model.addAttribute("query", query);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "products/search-results";
    }
}