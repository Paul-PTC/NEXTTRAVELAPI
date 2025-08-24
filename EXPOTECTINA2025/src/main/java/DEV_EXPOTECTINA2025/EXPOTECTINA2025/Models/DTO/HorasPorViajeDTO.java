package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class HorasPorViajeDTO {
    @NotNull(message = "El ID de las HorasViaje es obligatorio")
    private Long idHorasViaje;

   /* @NotNull(message = "El ID de DuiEmpleado es obligatorio")
    private EmpleadoEntities Empleado ;*/

    @NotNull(message = "El DUI de empleado es obligatorio")
    private String empleado;

    @NotNull(message = "El ID de Ruta es obligatorio")
    private Long idRuta;

    @NotNull(message = "La fecha de viaje es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaViaje;

    @NotNull(message = "La Hora de salida es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime horaSalida;

    @NotNull(message = "La Hora de llegada es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime horaLlegada;

    @NotBlank(message = "La duracion es obligatoria")
    private String duracion;

    @NotNull(message = "El ID del Vehículo es obligatorio")
    private Long idVehiculo;

}
