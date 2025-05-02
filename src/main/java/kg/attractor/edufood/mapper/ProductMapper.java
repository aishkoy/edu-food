package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.ProductDto;
import kg.attractor.edufood.entity.Product;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface ProductMapper {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);
}