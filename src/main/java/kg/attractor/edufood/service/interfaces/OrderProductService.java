package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.OrderProductDto;
import kg.attractor.edufood.entity.OrderProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderProductService {
    List<OrderProductDto> getAllByOrderId(Long orderId4);

    void deleteAllByOrderId(Long orderId);

    void deleteByOrderAndProduct(Long orderId, Long productId);

    void saveEntity(OrderProduct orderProduct);

    Optional<OrderProduct> getByOrderIdAndProductId(Long orderId, Long productId);

    void save(OrderProductDto orderProduct);

    BigDecimal calculateTotalAmountByOrderId(Long orderId);

    int calculateTotalQuantityByOrderId(Long orderId);
}
