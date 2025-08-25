package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItinerarioEmpleadoDTO {

    private Long idItinerario;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    @Size(max = 10, message = "El DUI no puede superar los 10 caracteres")
    private String duiEmpleado;

    @NotNull(message = "El id del vehículo es obligatorio")
    private Long idVehiculo;

    @NotNull(message = "El id de la ruta es obligatorio")
    private Long idRuta;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    @Size(max = 200, message = "Las observaciones no pueden superar los 200 caracteres")
    private String observaciones;
}
