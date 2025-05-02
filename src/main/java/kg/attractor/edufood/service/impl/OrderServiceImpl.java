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
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderDto> getAllUserOrders(Long userId, int page, int size, String sortDirection) {
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        return getOrderPage(() -> orderRepository.findByUserId(userId, pageable),
                "Не было найдено заказов на этой странице!");
    }

    @Override
    public Page<OrderDto> getOrdersAfterDate(Long userId, LocalDate date, int page, int size, String sortDirection) {
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        Timestamp timestamp = convertToTimestamp(date);
        return getOrderPage(() -> orderRepository.findByUserIdAndDateAfter(userId, timestamp, pageable),
                "Не было найдено заказов после указанной даты!");
    }

    @Override
    public Page<OrderDto> getOrdersBeforeDate(Long userId, LocalDate date, int page, int size, String sortDirection) {
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        Timestamp timestamp = convertToTimestamp(date);
        return getOrderPage(() -> orderRepository.findByUserIdAndDateBefore(userId, timestamp, pageable),
                "Не было найдено заказов до указанной даты!");
    }

    @Override
    public Page<OrderDto> getOrdersBetweenDates(Long userId, LocalDate dateFrom, LocalDate dateTo, int page, int size, String sortDirection) {
        Pageable pageable = createPageableWithSort(page, size, sortDirection);
        Timestamp timestampFrom = convertToTimestamp(dateFrom);
        Timestamp timestampTo = convertToTimestamp(dateTo);
        return getOrderPage(() -> orderRepository.findByUserIdAndDateBetween(userId, timestampFrom, timestampTo, pageable),
                "Не было найдено заказов в заданном промежутке дат!");
    }

    private Pageable createPageableWithSort(int page, int size, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(page - 1, size, direction);
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