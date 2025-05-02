package kg.attractor.edufood.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OrderProductDto {
    OrderDto order;
    ProductDto product;
    Integer quantity;
}
