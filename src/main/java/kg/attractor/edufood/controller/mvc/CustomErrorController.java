package kg.attractor.edufood.controller.mvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        log.error("Error occurred: Status = {}", status);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
                model.addAttribute("errorIcon", "search");
                model.addAttribute("errorTitle", "Страница не найдена");
                model.addAttribute("errorSubtitle", "Запрашиваемый ресурс не существует");
                model.addAttribute("errorMessage", "Запрашиваемая страница не найдена. Возможно, она была удалена или перемещена, или вы ввели неверный адрес.");
                return "error/error404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
                model.addAttribute("errorIcon", "shield-alert");
                model.addAttribute("errorTitle", "Доступ запрещен");
                model.addAttribute("errorSubtitle", "Недостаточно прав для доступа");
                model.addAttribute("errorMessage", "У вас недостаточно прав для доступа к запрашиваемому ресурсу. Пожалуйста, войдите в систему или обратитесь к администратору.");
                return "error/error403";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("reason", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                model.addAttribute("errorIcon", "alert-triangle");
                model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
                model.addAttribute("errorSubtitle", "Что-то пошло не так");
                model.addAttribute("errorMessage", "Произошла внутренняя ошибка сервера. Наша команда уже работает над решением проблемы. Пожалуйста, повторите попытку позже.");
                return "error/error500";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
                model.addAttribute("errorIcon", "file-warning");
                model.addAttribute("errorTitle", "Некорректный запрос");
                model.addAttribute("errorSubtitle", "Ошибка в запросе");
                model.addAttribute("errorMessage", "Ваш запрос не может быть обработан из-за ошибки в данных. Пожалуйста, проверьте введенную информацию и попробуйте снова.");
                return "error/error400";
            }
        }
        
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("reason", "Ошибка");
        model.addAttribute("errorIcon", "alert-circle");
        model.addAttribute("errorTitle", "Произошла ошибка");
        model.addAttribute("errorSubtitle", "Что-то пошло не так");
        model.addAttribute("errorMessage", "Произошла ошибка при обработке вашего запроса. Пожалуйста, попробуйте снова или свяжитесь с нашей службой поддержки.");
        
        return "error/error-generic";
    }
}