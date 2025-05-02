package kg.attractor.edufood.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OrderDto {
    Long id;

    UserDto user;

    OrderStatusDto status;

    @Builder.Default
    List<OrderProductDto> orderProducts = new ArrayList<>();

    @Builder.Default
    BigDecimal totalAmount = BigDecimal.ZERO;

    @Builder.Default
    Timestamp date = null;
}
