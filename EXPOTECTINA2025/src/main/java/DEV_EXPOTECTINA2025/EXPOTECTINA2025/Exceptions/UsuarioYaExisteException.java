package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions;

public class UsuarioYaExisteException extends RuntimeException {
    public UsuarioYaExisteException(String message) {
        super(message);
    }
}
