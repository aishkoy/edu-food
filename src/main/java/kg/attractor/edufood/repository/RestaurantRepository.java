package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findByNameStartingWith(String name, Pageable pageable);
    Page<Restaurant> findByNameContaining(String name, Pageable pageable);

    @Query(value = """
            SELECT r.* FROM restaurants r 
            LEFT JOIN products p ON r.id = p.restaurant_id 
            LEFT JOIN order_products op ON p.id = op.product_id 
            LEFT JOIN orders o ON op.order_id = o.id 
            GROUP BY r.id 
            ORDER BY COUNT(DISTINCT o.id) DESC 
            LIMIT :limit""", nativeQuery = true)
    List<Restaurant> findMostOrderedRestaurants(@Param("limit") int limit);
}
