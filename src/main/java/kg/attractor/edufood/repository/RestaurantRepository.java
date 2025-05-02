package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findByNameStartingWith(String name, Pageable pageable);
    Page<Restaurant> findByNameContaining(String name, Pageable pageable);
}
