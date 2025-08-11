package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReservaDTO {
    private Long id;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    private String duiEmpleado;

    @NotNull(message = "El ID de ruta es obligatorio")
    private Long idRuta;

    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDateTime  fechaReserva;

    @NotNull(message = "La fecha de viaje es obligatoria")
    private LocalDateTime fechaViaje;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "La cantidad de pasajeros es obligatoria")
    @Min(value = 1, message = "Debe haber al menos 1 pasajero")
    private Long cantidadPasajeros;

    private String descripcion;
}
