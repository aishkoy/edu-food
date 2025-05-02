package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.OrderProduct;
import kg.attractor.edufood.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    List<OrderProduct> findAllByOrderId(Long orderId);
}
