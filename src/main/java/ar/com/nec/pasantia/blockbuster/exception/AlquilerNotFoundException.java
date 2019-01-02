package ar.com.nec.pasantia.blockbuster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AlquilerNotFoundException extends RuntimeException {

    public AlquilerNotFoundException(String mensaje, Throwable error) {
        super(mensaje, error);
    }

    public AlquilerNotFoundException() {
        super();
    }

    public AlquilerNotFoundException(Throwable error) {
        super(error);
    }

    public AlquilerNotFoundException(String mensaje) {
        super(mensaje);
    }

}
