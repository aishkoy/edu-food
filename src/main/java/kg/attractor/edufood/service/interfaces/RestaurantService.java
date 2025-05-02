package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> getMostOrderedRestaurants(int limit);

    RestaurantDto getRestaurantById(Long restaurantId);

    List<RestaurantDto> getAllRestaurants();

    ResponseEntity<?> getRestaurantImage(Long restaurantId);

    Pageable createPageableWithSort(int page, int size, String sortDirection, String sortBy);

    Page<RestaurantDto> getRestaurants(String name, Pageable pageable);
}
