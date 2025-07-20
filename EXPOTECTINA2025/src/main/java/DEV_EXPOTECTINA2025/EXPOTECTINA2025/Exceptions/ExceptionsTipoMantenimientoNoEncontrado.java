package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions;

public class ExceptionsTipoMantenimientoNoEncontrado extends RuntimeException {
  public  ExceptionsTipoMantenimientoNoEncontrado(String message, Throwable cause) {
    super(message, cause);
  }
  public  ExceptionsTipoMantenimientoNoEncontrado(Throwable cause) {
    super(cause);
  }
    public ExceptionsTipoMantenimientoNoEncontrado(String message) {
        super(message);
    }
}
