package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderProductMapper.class})
public interface OrderMapper {
    @Mapping(target = "user.orders", ignore = true)
    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);
}
