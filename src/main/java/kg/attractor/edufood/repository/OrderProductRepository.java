package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.OrderProduct;
import kg.attractor.edufood.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    List<OrderProduct> findAllByOrderId(Long orderId);
    Optional<OrderProduct> findByOrderIdAndProductId(Long orderId, Long productId);

    @Modifying
    @Query("delete from OrderProduct op where op.order.id = :orderId and op.product.id = :productId")
    void deleteByOrderIdAndProductId(@Param("orderId") Long orderId, @Param("productId") Long productId);
}
