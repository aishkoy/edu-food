package kg.attractor.edufood.controller.api;

import kg.attractor.edufood.service.interfaces.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("{id}/image")
    public ResponseEntity<?> getImage(@PathVariable("id") Long restaurantId) {
        return restaurantService.getRestaurantImage(restaurantId);
    }
}
