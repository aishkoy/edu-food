package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductDto> getMostOrderedProducts(int limit);

    ProductDto getProductById(Long id);

    Page<ProductDto> getFilteredProducts(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                         Long categoryId, Long restaurantId, Pageable pageable);

    Page<ProductDto> getAllRestaurantsProducts(Long restaurantId, Pageable pageable);

    Page<ProductDto> getProductsByName(String name, Pageable pageable);

    Page<ProductDto> getProductsByCategoryId(Long categoryId, Pageable pageable);

    ResponseEntity<?> getProductImage(Long productId);

    Pageable createPageableWithSort(int page, int size, String sortDirection, String sortBy);
}
