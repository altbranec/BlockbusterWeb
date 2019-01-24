package ar.com.nec.pasantia.blockbuster.exception;

public class AlquilerFoundException extends RuntimeException {
    public AlquilerFoundException(String mensaje, Throwable error) {
        super(mensaje, error);
    }

    public AlquilerFoundException() {
        super();
    }

    public AlquilerFoundException(Throwable error) {
        super(error);
    }

    public AlquilerFoundException(String mensaje) {
        super(mensaje);
    }
}
