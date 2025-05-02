package kg.attractor.edufood.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    List<OrderProductDto> orderProducts = new ArrayList<>();
}
