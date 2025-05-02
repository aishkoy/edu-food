package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByRestaurantId(Long restaurantId, Pageable pageable);

    Page<Product> findByNameStartingWith(String name, Pageable pageable);
    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByPriceGreaterThanEqual(BigDecimal price, Pageable pageable);
    Page<Product> findByPriceLessThanEqual(BigDecimal price, Pageable pageable);
    Page<Product> findByPriceBetween(BigDecimal priceFrom, BigDecimal priceTo, Pageable pageable);

    Page<Product> findByCategoryIdAndRestaurantId(Long categoryId, Long restaurantId, Pageable pageable);

    Page<Product> findByNameStartingWithAndCategoryId(String name, Long categoryId, Pageable pageable);
    Page<Product> findByNameContainingAndCategoryId(String name, Long categoryId, Pageable pageable);

    Page<Product> findByNameStartingWithAndRestaurantId(String name, Long restaurantId, Pageable pageable);
    Page<Product> findByNameContainingAndRestaurantId(String name, Long restaurantId, Pageable pageable);

    Page<Product> findByNameStartingWithAndCategoryIdAndRestaurantId(String name, Long categoryId, Long restaurantId, Pageable pageable);
    Page<Product> findByNameContainingAndCategoryIdAndRestaurantId(String name, Long categoryId, Long restaurantId, Pageable pageable);

    Page<Product> findByPriceBetweenAndCategoryId(BigDecimal priceFrom, BigDecimal priceTo, Long categoryId, Pageable pageable);
    Page<Product> findByPriceBetweenAndRestaurantId(BigDecimal priceFrom, BigDecimal priceTo, Long restaurantId, Pageable pageable);
    Page<Product> findByPriceBetweenAndCategoryIdAndRestaurantId(BigDecimal priceFrom, BigDecimal priceTo, Long categoryId, Long restaurantId, Pageable pageable);

    Page<Product> findByNameContainingAndPriceBetween(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<Product> findByNameStartingWithAndPriceBetween(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    Page<Product> findByNameContainingAndPriceBetweenAndCategoryId(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Pageable pageable);
    Page<Product> findByNameStartingWithAndPriceBetweenAndCategoryId(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Pageable pageable);

    Page<Product> findByNameContainingAndPriceBetweenAndRestaurantId(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Long restaurantId, Pageable pageable);
    Page<Product> findByNameStartingWithAndPriceBetweenAndRestaurantId(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Long restaurantId, Pageable pageable);

    Page<Product> findByNameContainingAndPriceBetweenAndCategoryIdAndRestaurantId(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Long restaurantId, Pageable pageable);
    Page<Product> findByNameStartingWithAndPriceBetweenAndCategoryIdAndRestaurantId(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Long restaurantId, Pageable pageable);

    @Query(value = """
        SELECT p.* FROM products p 
        LEFT JOIN order_products op ON p.id = op.product_id 
        GROUP BY p.id 
        ORDER BY SUM(COALESCE(op.QUANTITY, 0)) DESC 
        LIMIT :limit""", nativeQuery = true)
    List<Product> findMostOrderedProducts(@Param("limit") int limit);
}
