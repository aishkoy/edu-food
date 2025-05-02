package kg.attractor.edufood.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(Model model, HttpServletRequest request, MultipartException e) {
        log.error("MultipartException processing error: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "file-warning");
        model.addAttribute("errorTitle", "Ошибка загрузки файла");
        model.addAttribute("errorSubtitle", "Проблема с загружаемым файлом");
        model.addAttribute("errorMessage", "Произошла ошибка при загрузке файла. Пожалуйста, убедитесь, что файл имеет правильный формат и размер не превышает допустимый.");
        return "error/error400";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleISE(Model model, HttpServletRequest request, IllegalStateException e) {
        log.error("IllegalStateException processing error: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "file-warning");
        model.addAttribute("errorTitle", "Некорректное состояние");
        model.addAttribute("errorSubtitle", "Некорректный запрос");
        model.addAttribute("errorMessage", "Система не может обработать ваш запрос в текущем состоянии. Пожалуйста, попробуйте позже или обратитесь к администратору.");
        return "error/error400";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(Model model, HttpServletRequest request, RuntimeException e) {
        log.error("Runtime exception: {}", e.getMessage(), e);
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "alert-triangle");
        model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
        model.addAttribute("errorSubtitle", "Что-то пошло не так");
        model.addAttribute("errorMessage", "Произошла внутренняя ошибка сервера. Наша команда уже работает над решением проблемы. Пожалуйста, повторите попытку позже.");
        return "error/error409";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIAE(Model model, HttpServletRequest request, IllegalArgumentException e) {
        log.error("Illegal argument exception: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "file-warning");
        model.addAttribute("errorTitle", "Некорректные данные");
        model.addAttribute("errorSubtitle", "Ошибка в запросе");
        model.addAttribute("errorMessage", "В вашем запросе содержатся некорректные данные. Пожалуйста, проверьте введенную информацию и попробуйте снова.");
        return "error/error400";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(Model model, HttpServletRequest request, ConstraintViolationException ex) {
        log.error("Constraint violation: {}", ex.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "file-warning");
        model.addAttribute("errorTitle", "Ошибка валидации");
        model.addAttribute("errorSubtitle", "Некорректные данные");
        model.addAttribute("errorMessage", "Введенные данные не соответствуют требованиям. Пожалуйста, проверьте форму и исправьте ошибки.");
        return "error/error400";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNSEE(Model model, HttpServletRequest request, NoSuchElementException e) {
        log.error("No such element exception: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "search");
        model.addAttribute("errorTitle", "Ресурс не найден");
        model.addAttribute("errorSubtitle", "Запрашиваемый ресурс не существует");
        model.addAttribute("errorMessage", "Запрашиваемый ресурс не найден в системе. Возможно, он был удален или перемещен.");
        return "error/error404";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(Model model, HttpServletRequest request, DataIntegrityViolationException e) {
        log.error("Data integrity violation: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "database");
        model.addAttribute("errorTitle", "Ошибка целостности данных");
        model.addAttribute("errorSubtitle", "Конфликт данных");
        model.addAttribute("errorMessage", "Произошла ошибка целостности данных. Возможно, данные, которые вы пытаетесь сохранить, уже существуют или нарушают правила системы.");
        return "error/generic-error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(Model model, HttpServletRequest request, AccessDeniedException e) {
        log.error("Access denied: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "shield-alert");
        model.addAttribute("errorTitle", "Доступ запрещен");
        model.addAttribute("errorSubtitle", "Недостаточно прав для доступа");
        model.addAttribute("errorMessage", "У вас недостаточно прав для доступа к запрашиваемому ресурсу. Пожалуйста, войдите в систему или обратитесь к администратору.");
        return "error/error403";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(Model model, HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.getMessage());
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        model.addAttribute("errorIcon", "file-warning");
        model.addAttribute("errorTitle", "Ошибка валидации");
        model.addAttribute("errorSubtitle", "Некорректные данные формы");
        model.addAttribute("errorMessage", "Форма содержит некорректные данные. Пожалуйста, проверьте и исправьте ошибки в полях формы.");
        return "error/error400";
    }
}