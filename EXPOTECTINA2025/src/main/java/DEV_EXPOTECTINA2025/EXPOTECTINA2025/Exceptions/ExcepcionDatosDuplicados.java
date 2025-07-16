package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions;

import lombok.Getter;

public class ExcepcionDatosDuplicados extends RuntimeException {

    @Getter
    private String campoDuplicado;

    public ExcepcionDatosDuplicados(String message, String campoDuplicado) {
        super(message);
        this.campoDuplicado = campoDuplicado;
    }

    public ExcepcionDatosDuplicados(String campoDuplicado) {
        this.campoDuplicado = campoDuplicado;
    }
}
