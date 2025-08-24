package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UbicacionEmpleadoDTO {

    private Long idUbicacionEmpleado;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    @Size(max = 10, message = "El DUI no puede superar los 10 caracteres")
    private String duiEmpleado;

    @NotNull(message = "La latitud es obligatoria")
    @Digits(integer = 10, fraction = 6, message = "Latitud debe tener hasta 10 enteros y 6 decimales")
    private BigDecimal latitud;

    @NotNull(message = "La longitud es obligatoria")
    @Digits(integer = 10, fraction = 6, message = "Longitud debe tener hasta 10 enteros y 6 decimales")
    private BigDecimal longitud;

    @NotNull(message = "La fecha/hora de registro es obligatoria")
    private LocalDateTime fechaHoraRegistroE;
}
