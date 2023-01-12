package pl.maciejowsky.bankapp.exceptions;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExcController {
    // it work only if the the request is handled by dispatcherServlet
//    @ExceptionHandler(value = Exception.class)
//    public String handleException(Exception e, Model model) {
//        model.addAttribute("err", e.getMessage());
//        return "error";
//    }

    @ExceptionHandler(value = PermissionDeniedException.class)
    public String handlePermissionDeniedException(Exception e) {

        String message = e.getMessage();
        return "redirect:/users?orderBy=name&page=1&error="+message;
    }
    @ExceptionHandler(value = NoUserFoundException.class)
    public String handleNoUserFoundException(Exception e) {
        String message = e.getMessage();
        return "redirect:/users?orderBy=name&page=1&error="+message;
    }

//    @ExceptionHandler(value = UserAlreadyExistException.class)
//    public String userAlreadyExistException(Exception e, Model model) {
//        String message = e.getMessage();
//
//    }
}
