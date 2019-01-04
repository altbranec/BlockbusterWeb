package ar.com.nec.pasantia.blockbuster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GenerosNotFoundException extends RuntimeException {

    public GenerosNotFoundException(String mensaje, Throwable error) {
        super(mensaje, error);
    }

    public GenerosNotFoundException() {
        super();
    }

    public GenerosNotFoundException(Throwable error) {
        super(error);
    }

    public GenerosNotFoundException(String mensaje) {
        super(mensaje);
    }

}
