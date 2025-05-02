package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.OrderProductDto;
import kg.attractor.edufood.entity.OrderProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderProductMapper{
    OrderProductDto toDto(OrderProduct product);
    OrderProduct toEntity(OrderProductDto dto);
}