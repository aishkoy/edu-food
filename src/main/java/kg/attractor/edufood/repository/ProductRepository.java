package kg.attractor.edufood.repository;

import kg.attractor.edufood.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = """
        SELECT p.* FROM products p 
        LEFT JOIN order_products op ON p.id = op.product_id 
        GROUP BY p.id 
        ORDER BY SUM(COALESCE(op.QUANTITY, 0)) DESC 
        LIMIT :limit""", nativeQuery = true)
    List<Product> findMostOrderedProducts(@Param("limit") int limit);
}