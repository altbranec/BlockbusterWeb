package ar.com.nec.pasantia.blockbuster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PeliculaNotFoundException extends RuntimeException {

    public PeliculaNotFoundException(String mensaje, Throwable error) {
        super(mensaje, error);
    }

    public PeliculaNotFoundException() {
        super();
    }

    public PeliculaNotFoundException(Throwable error) {
        super(error); }

    public PeliculaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
