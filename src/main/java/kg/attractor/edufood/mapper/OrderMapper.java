package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);
}
