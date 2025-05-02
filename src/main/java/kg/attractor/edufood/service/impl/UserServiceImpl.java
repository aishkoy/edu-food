package kg.attractor.edufood.service.impl;

import kg.attractor.edufood.dto.UserDto;
import kg.attractor.edufood.entity.User;
import kg.attractor.edufood.exception.nsee.UserNotFoundException;
import kg.attractor.edufood.mapper.UserMapper;
import kg.attractor.edufood.repository.UserRepository;
import kg.attractor.edufood.service.interfaces.UserService;
import kg.attractor.edufood.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDto register(UserDto dto){
        log.info("Нормализация полей");
        dto.setName(normalizeField(dto.getName(), true));
        dto.setSurname(normalizeField(dto.getSurname(), true));
        dto.setEmail(normalizeField(dto.getEmail(), false));
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setAddress(normalizeField(dto.getAddress(), false));

        User user = userMapper.toEntity(dto);
        userRepository.save(user);
        log.info("Зарегистрирован новый пользователь: {}", user.getId());
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Не был найден пользователь с таким id!"));
        log.info("Получен пользователь по id: {}", userId);
        return userMapper.toDto(user);
    }

    @Override
    public boolean existUserByEmail(String email){
        log.info("Проверка существования пользователя по id");
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Не существует пользователя с таким email: " + email));
        log.info("Получен пользователь по email: {}", email);
        return userMapper.toDto(user);
    }

    @Override
    public MultipartFile uploadAvatar(MultipartFile file, Long authId) {
        String contentType = file.getContentType();

        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new IllegalArgumentException("Только файлы JPEG и PNG разрешены для загрузки");
        }

        userRepository.updateUserAvatar(authId, saveImage(file));
        return file;
    }

    public String saveImage(MultipartFile file) {
        return FileUtil.saveUploadFile(file, "images/");
    }

    @Override
    public ResponseEntity<?> getUserAvatar(Long userId) {
        UserDto userDto = getUserById(userId);

        String filename = userDto.getAvatar();
        if(filename == null || filename.isBlank()){
            return FileUtil.getStaticFile("default_avatar.jpg", "images/", MediaType.IMAGE_JPEG);
        }

        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        MediaType mediaType = extension.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;

        try{
            return FileUtil.getOutputFile(filename, "images/", mediaType);
        } catch (NoSuchElementException e){
            return FileUtil.getStaticFile("default_avatar.jpg", "images/", MediaType.IMAGE_JPEG);
        }
    }

    @Override
    public void updateUser(Long userId, UserDto userDto){
        UserDto user = getUserById(userId);
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAddress(userDto.getAddress());

        log.info("Изменение данных пользователя: {}", user.getId());
        userRepository.save(userMapper.toEntity(user));
    }

    @Override
    public UserDto getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            log.info("Authorities: {}", authentication.getAuthorities());
        }

        if (authentication == null) {
            log.error("Authentication is null");
            throw new NoSuchElementException("user not authorized");
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            log.error("Authentication is anonymous");
            throw new IllegalArgumentException("user not authorized");
        }

        String email = authentication.getName();
        log.info("Поиск юзера по email: {}", email);
        return getUserByEmail(email);
    }

    private String normalizeField(String field, boolean capitalize) {
        if (field == null || field.isBlank()) {
            return null;
        }

        String normalized = field.trim().toLowerCase();
        return capitalize ? StringUtils.capitalize(normalized) : normalized;
    }
}
