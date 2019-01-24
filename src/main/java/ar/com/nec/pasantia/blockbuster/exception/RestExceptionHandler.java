package ar.com.nec.pasantia.blockbuster.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ClienteNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Cliente no encontrado",
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ ClienteIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    public void errorPager(RuntimeException e, Model model){
        model.addAttribute("error", e.getClass());
        model.addAttribute("status", "detenido");
        model.addAttribute("message", e.getMessage());
    }
}

