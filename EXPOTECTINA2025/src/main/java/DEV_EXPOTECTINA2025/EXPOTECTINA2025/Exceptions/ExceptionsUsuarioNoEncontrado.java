package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions;

public class ExceptionsUsuarioNoEncontrado extends RuntimeException{

    public ExceptionsUsuarioNoEncontrado(String message) {
        super(message);
    }

    public ExceptionsUsuarioNoEncontrado(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionsUsuarioNoEncontrado(Throwable cause) {
        super(cause);
    }
}
