package pl.maciejowsky.bankapp.exceptions;


import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExcController {
// it work only if the the request is handled by dispatcherServlet
//    @ExceptionHandler(value = Exception.class)
//    public String handleException() {
//        return "bad-request";
//    }

}
