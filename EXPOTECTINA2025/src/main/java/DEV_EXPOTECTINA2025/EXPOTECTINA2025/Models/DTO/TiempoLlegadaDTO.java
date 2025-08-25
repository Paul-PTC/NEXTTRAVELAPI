package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

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
public class TiempoLlegadaDTO {


    private Long idTiempoestimado;

    private Long idRuta;

    private Long idReserva;
    @NotNull(message = "El Tiempo es obligatorio")
    private Long tiempoEstimado;
    @NotNull(message = "la Fecha  es obligatorio")
    private LocalDateTime FechaHoraCalculo;

}
