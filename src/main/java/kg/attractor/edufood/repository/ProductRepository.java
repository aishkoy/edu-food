package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByRestaurantId(Long restaurantId, Pageable pageable);
    Page<Product> findByNameStartingWith(String name, Pageable pageable);
    Page<Product> findByNameContaining(String name, Pageable pageable);
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> findByPriceGreaterThanEqual(BigDecimal price, Pageable pageable);
    Page<Product> findByPriceLessThanEqual(BigDecimal price, Pageable pageable);
    Page<Product> findByPriceBetween(BigDecimal priceFrom, BigDecimal priceTo, Pageable pageable);
}
