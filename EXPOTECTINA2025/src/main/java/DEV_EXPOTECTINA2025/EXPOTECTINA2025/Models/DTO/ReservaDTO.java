package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReservaDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long idReserva;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    private String duiEmpleado;

    @NotNull(message = "El ID de la ruta es obligatorio")
    private Long idRuta;

    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fechaReserva;

    @NotNull(message = "La fecha de viaje es obligatoria")
    private LocalDate fechaViaje;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "La cantidad de pasajeros es obligatoria")
    @Min(value = 1, message = "Debe haber al menos 1 pasajero")
    private Integer cantidadPasajeros;

    @Size(max = 200, message = "La descripción no debe superar los 200 caracteres")
    private String descripcion;
}
