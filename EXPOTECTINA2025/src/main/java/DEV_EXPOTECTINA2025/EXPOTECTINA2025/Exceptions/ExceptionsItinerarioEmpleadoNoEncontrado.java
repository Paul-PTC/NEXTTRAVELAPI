package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions;

public class ExceptionsItinerarioEmpleadoNoEncontrado extends RuntimeException {

    public  ExceptionsItinerarioEmpleadoNoEncontrado(String message, Throwable cause) {
        super(message, cause);
    }
    public  ExceptionsItinerarioEmpleadoNoEncontrado(Throwable cause) {
        super(cause);
    }
    public ExceptionsItinerarioEmpleadoNoEncontrado(String message) {
        super(message);
    }
    }


