package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class HistorialGastoDTO {

    private Long idHistorialGasto;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    private String duiEmpleado;

    @NotNull(message = "La fecha del gasto es obligatoria")
    private LocalDate fechaGasto;

    @NotNull(message = "El total del gasto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El gasto debe ser un número positivo")
    private Double totalGasto;

    private String observaciones;
}
