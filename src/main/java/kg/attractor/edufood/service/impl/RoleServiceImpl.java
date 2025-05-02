package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.RoleDto;
import kg.attractor.edufood.entity.Role;
import kg.attractor.edufood.exception.nsee.RoleNotFoundException;
import kg.attractor.edufood.mapper.RoleMapper;
import kg.attractor.edufood.repository.RoleRepository;
import kg.attractor.edufood.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto getRoleByName(String name) {
        String roleName = name.toUpperCase();
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Роль с таким именем не была найдена!"));
        log.info("Получена роль по имени {}", roleName);
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            throw new RoleNotFoundException("Роли не были найдены!");
        }

        log.info("Получено ролей: {}", roles.size());
        return roles.stream()
                .map(roleMapper::toDto)
                .toList();
    }
}
