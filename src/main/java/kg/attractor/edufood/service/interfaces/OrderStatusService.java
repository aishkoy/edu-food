package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.OrderStatusDto;

import java.util.List;

public interface OrderStatusService {
    OrderStatusDto getOrderStatusById(Long id);

    OrderStatusDto getOrderStatusByName(String name);

    List<OrderStatusDto> getAllOrderStatuses();
}
