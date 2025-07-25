package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class ItinerarioEmpleadoDTO {

    @NotNull(message = "El ID de Itinerario es obligatorio")
    private Long idItinerario;

    @NotBlank(message = "El DUI es obligatorio")
    private String duiEmpleado;

    @NotNull(message = "El ID de vehiculo es obligatorio")
    private Long idVehiculo;

    @NotNull(message = "El ID de ruta es obligatorio")
    private Long idRuta;


    @NotBlank(message = "La fecha es obligatorio")
    private Date fecha;

    @NotBlank(message = "La hora de inicio es obligatorio")
    private Date horaInicio;

    @NotBlank(message = "La hora de finalizacion es obligatorio")
    private Date horaFin;

    @NotBlank(message = "La observacion es obligatoria")
    private String Observaciones;
}
