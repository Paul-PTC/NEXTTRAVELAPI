package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
public class HorasPorViajeDTO {

    private Long idHorasViaje;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    @Size(max = 10, message = "El DUI no puede superar los 10 caracteres")
    private String duiEmpleado;

    @NotNull(message = "El id de la ruta es obligatorio")
    private Long idRuta;

    private LocalDate fechaViaje;

    private LocalDateTime horaSalida;

    private LocalDateTime horaLlegada;

    @Size(max = 20, message = "La duración no puede superar los 20 caracteres")
    private String duracion;
}
