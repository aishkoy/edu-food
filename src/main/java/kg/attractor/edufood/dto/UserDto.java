package kg.attractor.edufood.dto;

import jakarta.validation.constraints.*;
import kg.attractor.edufood.validation.annotation.UniqueEmail;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserDto {
    Long id;

    @NotBlank
    String name;

    String surname;

    @Email
    @NotBlank
    @UniqueEmail
    String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "Длина пароля должна быть 8-20 символов")
    @Pattern(
            regexp = "^(?=.*[A-Za-zА-Яа-я])(?=.*\\d)[A-Za-zА-Яа-я\\d@#$%^&+=!]{8,}$",
            message = "Пароль должен содержать хотя бы одну букву (русскую или латинскую) и одну цифру"
    )
    String password;

    @NotBlank
    String address;

    @Builder.Default
    String avatar = null;

    @Builder.Default
    Boolean enabled = true;

    @NotNull
    RoleDto role;

    @Builder.Default
    List<OrderDto> orders = new ArrayList<>();
}
