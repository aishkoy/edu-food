package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.RestaurantDto;
import kg.attractor.edufood.entity.Restaurant;
import kg.attractor.edufood.exception.nsee.RestaurantNotFoundException;
import kg.attractor.edufood.mapper.RestaurantMapper;
import kg.attractor.edufood.repository.RestaurantRepository;
import kg.attractor.edufood.service.interfaces.RestaurantService;
import kg.attractor.edufood.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantDto getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Ресторан с таким id не был найден!"));
        log.info("Получен ресторан по id: {}", restaurantId);
        return restaurantMapper.toDto(restaurant);
    }

    @Override
    public Page<RestaurantDto> getRestaurantsByName(String name, int page, int size, String sortDirection) {
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        try {
            return getRestaurantPage(() -> restaurantRepository.findByNameStartingWith(name, pageable));
        } catch (NoSuchElementException e) {
            return getRestaurantPage(() -> restaurantRepository.findByNameContaining(name, pageable),
                    "На этой странице нет ресторанов с таким именем!");
        }
    }

    @Override
    public Page<RestaurantDto> getAllRestaurants(int page, int size, String sortDirection) {
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        return getRestaurantPage(() -> restaurantRepository.findAll(pageable),
                "Рестораны на этой странице не были найдены!");
    }

    @Override
    public ResponseEntity<?> getRestaurantImage(Long restaurantId) {
        RestaurantDto restaurantDto = getRestaurantById(restaurantId);

        if (restaurantDto.getImage() == null || restaurantDto.getImage().isEmpty()) {
            return FileUtil.getStaticFile("default_restaurant.jpg", "images/", MediaType.IMAGE_JPEG);
        }

        String filename = restaurantDto.getImage();
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        MediaType mediaType = extension.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;

        log.info("Получение фотографии для ресторана с id {}", restaurantId );
        return FileUtil.getOutputFile(filename, "images/", mediaType);
    }

    private Pageable createPageableWithSort(int page, int size, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(page - 1, size, direction);
    }

    private Page<RestaurantDto> getRestaurantPage(Supplier<Page<Restaurant>> supplier) {
        return getRestaurantPage(supplier, null);
    }

    private Page<RestaurantDto> getRestaurantPage(Supplier<Page<Restaurant>> supplier, String notFoundMessage) {
        Page<Restaurant> page = supplier.get();
        if (page.isEmpty()) {
            throw new RestaurantNotFoundException(
                    notFoundMessage != null ? notFoundMessage : "Рестораны не найдены"
            );
        }
        log.info("Получено {} ресторанов на странице", page.getSize());
        return page.map(restaurantMapper::toDto);
    }
}
