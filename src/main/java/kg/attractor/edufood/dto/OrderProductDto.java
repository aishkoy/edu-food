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

public class OrderProductDto {
    Long orderId;
    ProductDto product;
    Integer quantity;
    BigDecimal amount;
}
