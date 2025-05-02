package kg.attractor.edufood.service.interfaces;

import kg.attractor.edufood.dto.EditUserDto;
import kg.attractor.edufood.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    EditUserDto mapToEditUser(UserDto dto);

    UserDto register(UserDto dto);

    ResponseEntity<?> getUserAvatar(Long userId);

    MultipartFile uploadAvatar(MultipartFile file, Long authId);

    UserDto getUserById(Long userId);

    boolean existUserByEmail(String email);

    UserDto getUserByEmail(String email);

    void updateUser(Long userId, EditUserDto userDto);

    UserDto getAuthUser();
}
