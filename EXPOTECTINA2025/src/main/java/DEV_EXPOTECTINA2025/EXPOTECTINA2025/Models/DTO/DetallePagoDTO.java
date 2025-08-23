package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class DetallePagoDTO {


    private long idDetallePago;

    private long idPago;

    @NotNull(message = "Los Puntos Requeridos es obligatorio")
    @Size(max = 50, message = "EL minimos de caracteres es 50")
    private String metodo;

    @NotNull(message = "Los Puntos Requeridos es obligatorio")
    @Size(max = 100, message = "EL Maximo de caracteres es 100")
    private String referencia;

    @NotNull(message = "Los Puntos Requeridos es obligatorio")
    @Size(min = 255, message = "EL minimos de caracteres es 255")
    private String observacion;
}
