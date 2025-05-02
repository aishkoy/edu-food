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

    Page<ProductDto> getByPriceBetweenAndCategory(BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Pageable pageable);

    Page<ProductDto> getByPriceBetweenAndRestaurant(BigDecimal minPrice, BigDecimal maxPrice, Long restaurantId, Pageable pageable);

    Page<ProductDto> getByPriceBetweenAndCetgoryAndRestaurant(BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Long restaurantId, Pageable pageable);

    Page<ProductDto> getByNameAndRestaurant(String name, Long restaurantId, Pageable pageable);

    Page<ProductDto> getByNameAndCategory(String name, Long categoryId, Pageable pageable);

    Page<ProductDto> getByNameAndCategoryAndRestaurant(String name, Long categoryId, Long restaurantId, Pageable pageable);

    Page<ProductDto> getProductsByCategoryAndRestaurant(Long categoryId, Long restaurantId, Pageable pageable);

    Page<ProductDto> getAllRestaurantsProducts(Long restaurantId, Pageable pageable);

    Page<ProductDto> getAllProducts(Pageable pageable);

    Page<ProductDto> getProductsByName(String name, Pageable pageable);

    Page<ProductDto> getProductsByCategoryId(Long categoryId, Pageable pageable);

    Page<ProductDto> getProductsByPriceGreaterThanEqual(BigDecimal price, Pageable pageable);

    Page<ProductDto> getProductsByPriceLessThanEqual(BigDecimal price, Pageable pageable);

    Page<ProductDto> getProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<ProductDto> getProductsByNameAndPriceRange(String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<ProductDto> getByNameAndPriceRangeAndCategory(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                                       Long categoryId, Pageable pageable);

    Page<ProductDto> getByNameAndPriceRangeAndRestaurant(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                                         Long restaurantId, Pageable pageable);

    Page<ProductDto> getByNameAndPriceRangeAndCategoryAndRestaurant(String name, BigDecimal minPrice, BigDecimal maxPrice,
                                                                    Long categoryId, Long restaurantId, Pageable pageable);

    ResponseEntity<?> getProductImage(Long productId);

    Pageable createPageableWithSort(int page, int size, String sortDirection, String sortBy);
}
