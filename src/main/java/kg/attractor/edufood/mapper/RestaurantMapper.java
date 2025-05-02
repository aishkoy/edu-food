package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.RestaurantDto;
import kg.attractor.edufood.entity.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDto toDto(Restaurant restaurant);

    Restaurant toEntity(RestaurantDto restaurantDto);
}