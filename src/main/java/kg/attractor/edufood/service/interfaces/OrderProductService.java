package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.OrderProductDto;

import java.util.List;

public interface OrderProductService {
    List<OrderProductDto> getAllByOrderId(Long orderId4);
}
