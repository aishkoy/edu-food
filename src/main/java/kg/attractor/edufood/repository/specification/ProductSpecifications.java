package kg.attractor.edufood.repository.specification;

import kg.attractor.edufood.entity.Product;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

@UtilityClass
public class ProductSpecifications {

    public Specification<Product> hasName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String nameLowerCase = name.toLowerCase();
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("name")), nameLowerCase + "%"),
                cb.like(cb.lower(root.get("name")), "%" + nameLowerCase + "%")
        );
    }

    public Specification<Product> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null;
        }
        
        if (minPrice != null && maxPrice != null) {
            return (root, query, cb) -> cb.between(root.get("price"), minPrice, maxPrice);
        } else if (minPrice != null) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        } else {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        }
    }

    public Specification<Product> hasCategoryId(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

    public Specification<Product> hasRestaurantId(Long restaurantId) {
        if (restaurantId == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("restaurant").get("id"), restaurantId);
    }
}