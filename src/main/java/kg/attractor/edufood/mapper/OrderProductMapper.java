package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.OrderProductDto;
import kg.attractor.edufood.entity.Order;
import kg.attractor.edufood.entity.OrderProduct;
import kg.attractor.edufood.entity.OrderProductId;
import kg.attractor.edufood.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class OrderProductMapper {

    @Autowired
    protected ProductMapper productMapper;

    @Mapping(target = "product", source = "product")
    @Mapping(target = "product.restaurant.products", ignore = true)
    public abstract OrderProductDto toDto(OrderProduct orderProduct);
    
    public OrderProduct toEntity(OrderProductDto dto, Order order, Product product) {
        if (dto == null) {
            return null;
        }
        
        OrderProduct orderProduct = new OrderProduct();
        
        OrderProductId id = new OrderProductId();
        id.setOrderId(order.getId());
        id.setProductId(product.getId());
        
        orderProduct.setId(id);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(dto.getQuantity());
        
        return orderProduct;
    }
    
    public void updateOrderProductFromDto(OrderProductDto dto, @MappingTarget OrderProduct orderProduct) {
        if (dto == null) {
            return;
        }
        
        orderProduct.setQuantity(dto.getQuantity());
    }
}