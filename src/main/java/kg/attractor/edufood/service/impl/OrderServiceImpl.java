package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.entity.Order;
import kg.attractor.edufood.exception.nsee.OrderNotFoundException;
import kg.attractor.edufood.mapper.OrderMapper;
import kg.attractor.edufood.repository.OrderRepository;
import kg.attractor.edufood.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Supplier;

@Service("orderService")
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderDto> getAllUserOrders(Long userId, Pageable pageable) {
        return getOrderPage(() -> orderRepository.findByUserId(userId, pageable),
                "Не было найдено заказов на этой странице!");
    }

    @Override
    public Page<OrderDto> getOrdersAfterDate(Long userId, LocalDate date, Pageable pageable) {
        Timestamp timestamp = convertToTimestamp(date);
        return getOrderPage(() -> orderRepository.findByUserIdAndDateAfter(userId, timestamp, pageable),
                "Не было найдено заказов после указанной даты!");
    }

    @Override
    public Page<OrderDto> getOrdersBeforeDate(Long userId, LocalDate date, Pageable pageable) {
        Timestamp timestamp = convertToTimestamp(date);
        return getOrderPage(() -> orderRepository.findByUserIdAndDateBefore(userId, timestamp, pageable),
                "Не было найдено заказов до указанной даты!");
    }

    @Override
    public Page<OrderDto> getOrdersBetweenDates(Long userId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        Timestamp timestampFrom = convertToTimestamp(dateFrom);
        Timestamp timestampTo = convertToTimestamp(dateTo);
        return getOrderPage(() -> orderRepository.findByUserIdAndDateBetween(userId, timestampFrom, timestampTo, pageable),
                "Не было найдено заказов в заданном промежутке дат!");
    }

    @Override
    public List<OrderDto> getRecentOrdersByUserId(Long userId) {
        List<OrderDto> orders = orderRepository.findLastOrdersByUser(userId, 3)
                .stream()
                .map(orderMapper::toDto)
                .toList();
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("У пользователя пока нет заказов!");
        }
        log.info("Получены последние заказы для пользователя");
        return orders;
    }

    public boolean isAuthorOfOrder(Long orderId, Long userId) {
        Order order = getOrderById(orderId);
        return order.getUser().getId().equals(userId);
    }

    @Override
    public OrderDto getOrderDtoById(Long orderId) {
        return orderMapper.toDto(getOrderById(orderId));
    }

    @Override
    public Page<OrderDto> getFilteredOrders(Long userId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        Page<OrderDto> ordersPage;

        if (dateFrom != null && dateTo != null) {
            ordersPage = getOrdersBetweenDates(userId, dateFrom, dateTo, pageable);
        } else if (dateFrom != null) {
            ordersPage = getOrdersAfterDate(userId, dateFrom, pageable);
        } else if (dateTo != null) {
            ordersPage = getOrdersBeforeDate(userId, dateTo, pageable);
        } else {
            ordersPage = getAllUserOrders(userId, pageable);
        }

        return ordersPage;
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Не существует заказа с таким id!"));
    }

    @Override
    public Pageable createPageableWithSort(int page, int size, String sortDirection, String sortBy) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
    }

    @Override
    public Order save(OrderDto orderDto){
        Order order = orderMapper.toEntity(orderDto);
        log.info("Сохранение нового заказа");
        return orderRepository.save(order);
    }

    private Page<OrderDto> getOrderPage(Supplier<Page<Order>> supplier, String notFoundMessage) {
        Page<Order> page = supplier.get();
        if (page.isEmpty()) {
            throw new OrderNotFoundException(notFoundMessage);
        }
        log.info("Получено {} заказов на странице", page.getSize());
        return page.map(orderMapper::toDto);
    }

    private Timestamp convertToTimestamp(LocalDate date) {
        return Timestamp.valueOf(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
    }
}