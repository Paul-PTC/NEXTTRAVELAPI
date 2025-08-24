package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GastoDTO {

    private Long idGasto;

    @NotNull(message = "El tipo de gasto es obligatorio")
    private Long idTipoGasto;   // FK a TipoGasto

    @NotBlank(message = "El DUI del empleado es obligatorio")
    @Size(max = 10, message = "El DUI no puede tener más de 10 caracteres")
    private String duiEmpleado; // FK a Empleado

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.00", inclusive = false, message = "El monto debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "Formato de monto inválido")
    private BigDecimal monto;

    @NotNull(message = "La fecha del gasto es obligatoria")
    private LocalDate fechaGasto; // Oracle DATE → LocalDate

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;
}

