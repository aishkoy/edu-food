package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);

    Page<Order> findByUserIdAndDateAfter(Long userId, Timestamp date, Pageable pageable);

    Page<Order> findByUserIdAndDateBefore(Long userId, Timestamp date, Pageable pageable);

    Page<Order> findByUserIdAndDateBetween(Long userId, Timestamp dateFrom, Timestamp dateTo, Pageable pageable);

    @Query(value = """
            select * from ORDERS o
            where o.USER_ID = :userId 
            order by o."DATE" desc 
                            limit :limit """, nativeQuery = true)
    List<Order> findLastOrdersByUser(@Param("userId") Long userId, @Param("limit") int limit);
}
