package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto getRoleByName(String name);

    List<RoleDto> getAllRoles();
}
