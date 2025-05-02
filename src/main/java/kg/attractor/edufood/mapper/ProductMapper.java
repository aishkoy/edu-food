package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.ProductDto;
import kg.attractor.edufood.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface ProductMapper {
    @Mapping(target = "restaurant.products", ignore = true)
    ProductDto toDto(Product product);

    @Mapping(target = "orderProducts", ignore = true)
    Product toEntity(ProductDto productDto);
}

