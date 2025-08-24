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
public class GananciaDTO {

    private Long idGanancia;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    @Size(max = 10, message = "El DUI no puede superar 10 caracteres")
    private String duiEmpleado;      // FK a Empleado (VARCHAR2(10))

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long idReserva;          // FK a Reserva (INT)

    @NotNull(message = "La fecha de ganancia es obligatoria")
    private LocalDate fechaGanancia; // Oracle DATE → LocalDate

    @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
    private String descripcion;

    @NotNull(message = "El monto es obligatorio")
    @Digits(integer = 10, fraction = 2, message = "Formato de monto inválido")
    @DecimalMin(value = "0.00", inclusive = false, message = "El monto debe ser mayor que 0")
    private BigDecimal monto;
}

