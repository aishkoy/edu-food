package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.OrderStatusDto;
import kg.attractor.edufood.exception.nsee.OrderStatusNotFoundException;
import kg.attractor.edufood.mapper.OrderStatusMapper;
import kg.attractor.edufood.repository.OrderStatusRepository;
import kg.attractor.edufood.service.interfaces.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusMapper orderStatusMapper;

    @Override
    public OrderStatusDto getOrderStatusById(Long id) {
        log.info("Получение статуса заказа по ID: {}", id);
        return orderStatusRepository.findById(id)
                .map(orderStatusMapper::toDto)
                .orElseThrow(() -> new OrderStatusNotFoundException("Статус заказа не найден с ID: " + id));
    }

    @Override
    public OrderStatusDto getOrderStatusByName(String name) {
        log.info("Получение статуса заказа по имени: {}", name);
        return orderStatusRepository.findByName(name)
                .map(orderStatusMapper::toDto)
                .orElseThrow(() -> new OrderStatusNotFoundException("Статус заказа не найден с именем: " + name));
    }

    @Override
    public List<OrderStatusDto> getAllOrderStatuses() {
        List<OrderStatusDto> statuses = orderStatusRepository.findAll()
                .stream()
                .map(orderStatusMapper::toDto)
                .toList();

        if (statuses.isEmpty()) {
            throw new OrderStatusNotFoundException("Не было найдено статусов заказов!");
        }

        log.info("Найдено {} статусов заказов", statuses.size());
        return statuses;
    }
}
