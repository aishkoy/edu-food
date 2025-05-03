package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.OrderStatusDto;
import kg.attractor.edufood.entity.OrderStatus;

import java.util.List;

public interface OrderStatusService {

    OrderStatus getOrderStatusByName(String name);

    OrderStatusDto getOrderStatusDtoByName(String name);

    List<OrderStatusDto> getAllOrderStatuses();
}
