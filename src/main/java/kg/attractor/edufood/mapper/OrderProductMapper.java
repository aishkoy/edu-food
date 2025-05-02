package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.OrderProductDto;
import kg.attractor.edufood.entity.OrderProduct;
import kg.attractor.edufood.entity.OrderProductId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {
    OrderProductDto toDto(OrderProduct product);

    @Mapping(target = "id", expression = "java(createOrderProductId(dto))")
    @Mapping(target = "order.id", source = "orderId")
    OrderProduct toEntity(OrderProductDto dto);

    default OrderProductId createOrderProductId(OrderProductDto dto) {
        if (dto.getOrderId() == null || (dto.getProduct().getId() == null)) {
            return null;
        }

        return OrderProductId.builder()
                .orderId(dto.getOrderId())
                .productId(dto.getProduct().getId())
                .build();
    }

    @AfterMapping
    default void setOrderAndProduct(@MappingTarget OrderProduct entity, OrderProductDto dto) {
        if (entity.getId() == null) {
            entity.setId(createOrderProductId(dto));
        }
    }
}