package kg.attractor.edufood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class EditUserDto {
    @NotBlank
    String name;

    String surname;

    @NotBlank
    String address;
}
