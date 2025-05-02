package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.ProductCategoryDto;
import kg.attractor.edufood.entity.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    ProductCategoryDto toDto(ProductCategory productCategory);

    ProductCategory toEntity(ProductCategoryDto productCategoryDto);
}
