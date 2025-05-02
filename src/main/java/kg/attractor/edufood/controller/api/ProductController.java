package kg.attractor.edufood.controller.api;

import kg.attractor.edufood.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("{id}/image")
    public ResponseEntity<?> getImage(@PathVariable("id") Long productId) {
        return productService.getProductImage(productId);
    }
}
