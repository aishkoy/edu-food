package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.OrderDto;
import kg.attractor.edufood.dto.UserDto;
import kg.attractor.edufood.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface OrderService {
    Page<OrderDto> getAllUserOrders(Long userId, Pageable pageable);

    Page<OrderDto> getOrdersAfterDate(Long userId, LocalDate date, Pageable pageable);

    Page<OrderDto> getOrdersBeforeDate(Long userId, LocalDate date, Pageable pageable);

    Page<OrderDto> getOrdersBetweenDates(Long userId, LocalDate dateFrom, LocalDate dateTo,Pageable pageable);

    List<OrderDto> getRecentOrdersByUserId(Long userId);

    OrderDto getOrderDtoById(Long orderId);

    Page<OrderDto>getFilteredOrders(Long userId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable);

    @Transactional
    Order getOrCreateDbCart(UserDto user);

    OrderDto mapToDto(Order order);

    Optional<Order> getByUserIdAndStatusId(Long userId, Long statusId);

    Pageable createPageableWithSort(int page, int size, String sortDirection, String sortBy);

    Order save(OrderDto orderDto);

    void delete(Order order);

    Order saveEntity(Order order);

    void updateTotals(Long orderId, BigDecimal totalAmount, int totalQuantity);
}
