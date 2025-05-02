package kg.attractor.edufood.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RestaurantDto {
    Long id;

    String name;

    String description;

    String image;

    String address;
}
