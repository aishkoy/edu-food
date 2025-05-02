package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.EditUserDto;
import kg.attractor.edufood.dto.UserDto;
import kg.attractor.edufood.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    EditUserDto toEditDto(UserDto dto);
}
