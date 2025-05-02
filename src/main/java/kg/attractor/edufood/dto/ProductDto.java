package kg.attractor.edufood.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductDto {
    Long id;

    String name;

    String description;

    BigDecimal price;

    ProductCategoryDto category;

    String image;

    RestaurantDto restaurant;
}
