package ar.com.nec.pasantia.blockbuster.exception;

public class ClienteFoundException extends RuntimeException {
    public ClienteFoundException(String mensaje) {
        super(mensaje);
    }
}
