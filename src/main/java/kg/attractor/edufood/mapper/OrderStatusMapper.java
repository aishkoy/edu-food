package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.OrderStatusDto;
import kg.attractor.edufood.entity.OrderStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {
    OrderStatusDto toDto(OrderStatus orderStatus);

    OrderStatus toEntity(OrderStatusDto orderStatusDto);
}
