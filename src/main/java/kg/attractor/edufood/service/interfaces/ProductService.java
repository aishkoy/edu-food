package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface ProductService {
    ProductDto getProductById(Long id);

    Page<ProductDto> getAllRestaurantsProducts(Long restaurantId, int page, int size, String sortDirection);

    Page<ProductDto> getAllProducts(int page, int size, String sortDirection);

    Page<ProductDto> getProductsByName(String name, int page, int size, String sortDirection);

    Page<ProductDto> getProductsByCategoryId(Long categoryId, int page, int size, String sortDirection);

    Page<ProductDto> getProductsByPriceGreaterThanEqual(BigDecimal price, int page, int size, String sortDirection);

    Page<ProductDto> getProductsByPriceLessThanEqual(BigDecimal price, int page, int size, String sortDirection);

    Page<ProductDto> getProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, int page, int size, String sortDirection);

    ResponseEntity<?> getProductImage(Long productId);
}
