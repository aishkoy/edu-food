package kg.attractor.edufood.mapper;

import kg.attractor.edufood.dto.RoleDto;
import kg.attractor.edufood.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);
}
