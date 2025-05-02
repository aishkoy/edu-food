package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.OrderDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;


public interface OrderService {
    Page<OrderDto> getAllUserOrders(Long userId, int page, int size, String sortDirection);

    Page<OrderDto> getOrdersAfterDate(Long userId, LocalDate date, int page, int size, String sortDirection);

    Page<OrderDto> getOrdersBeforeDate(Long userId, LocalDate date, int page, int size, String sortDirection);

    Page<OrderDto> getOrdersBetweenDates(Long userId, LocalDate dateFrom, LocalDate dateTo, int page, int size, String sortDirection);
}
