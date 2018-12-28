package ar.com.nec.pasantia.blockbuster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(String mensaje, Throwable error) {
        super(mensaje, error);
    }

    public ClienteNotFoundException() {
        super();
    }

    public ClienteNotFoundException(Throwable error) {
        super(error);
    }

    public ClienteNotFoundException(String mensaje) {
        super(mensaje);
    }

}
