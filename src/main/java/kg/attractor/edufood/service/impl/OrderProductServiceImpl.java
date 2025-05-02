package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.OrderProductDto;
import kg.attractor.edufood.entity.OrderProduct;
import kg.attractor.edufood.exception.nsee.ProductNotFoundException;
import kg.attractor.edufood.mapper.OrderProductMapper;
import kg.attractor.edufood.repository.OrderProductRepository;
import kg.attractor.edufood.service.interfaces.OrderProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductMapper orderProductMapper;
    private final OrderProductRepository orderProductRepository;

    @Override
    public List<OrderProductDto> getAllByOrderId(Long orderId) {
        return findAndValidate(() -> orderProductRepository.findAllByOrderId(orderId),
                "У данного заказа пока нет продуктов!");
    }

    private List<OrderProductDto> findAndValidate(Supplier<List<OrderProduct>> supplier, String notFoundMessage) {
        List<OrderProduct> orderProducts = supplier.get();
        if (orderProducts.isEmpty()) {
            throw new ProductNotFoundException(notFoundMessage);
        }
        log.info("Получено продуктов: {}", orderProducts.size());
        return orderProducts.stream().map(orderProductMapper::toDto).toList();
    }
}
