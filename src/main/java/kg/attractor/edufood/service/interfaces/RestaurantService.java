package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface RestaurantService {
    RestaurantDto getRestaurantById(Long restaurantId);

    Page<RestaurantDto> getRestaurantsByName(String name, int page, int size, String sortDirection);

    Page<RestaurantDto> getAllRestaurants(int page, int size, String sortDirection);

    ResponseEntity<?> getRestaurantImage(Long restaurantId);
}
