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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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

    @Override
    public void deleteAllByOrderId(Long orderId){
        List<OrderProduct> itemsToDelete = orderProductRepository.findAllByOrderId(orderId);
        orderProductRepository.deleteAll(itemsToDelete);
        orderProductRepository.flush();
    }

    @Override
    public void deleteByOrderAndProduct(Long orderId, Long productId){
        orderProductRepository.deleteByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public void saveEntity(OrderProduct orderProduct){
        log.info("Сохранен новый элемент заказа {}", orderProduct.getId());
        orderProductRepository.save(orderProduct);
    }

    @Override
    public Optional<OrderProduct> getByOrderIdAndProductId(Long orderId, Long productId){
        return orderProductRepository.findByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public void save(OrderProductDto orderProduct) {
        OrderProduct op = orderProductMapper.toEntity(orderProduct);
        saveEntity(op);
    }

    @Override
    public BigDecimal calculateTotalAmountByOrderId(Long orderId) {

        List<OrderProductDto> orderProducts = new ArrayList<>();
        try{
            orderProducts = getAllByOrderId(orderId);
        }catch (NoSuchElementException e){
        }

        if (orderProducts.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return orderProducts.stream()
                .map(OrderProductDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public int calculateTotalQuantityByOrderId(Long orderId) {
        List<OrderProductDto> orderProducts = new ArrayList<>();
        try{
            orderProducts = getAllByOrderId(orderId);
        }catch (NoSuchElementException e){
        }

        if (orderProducts.isEmpty()) {
            return 0;
        }

        return orderProducts.stream()
                .mapToInt(OrderProductDto::getQuantity)
                .sum();
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
