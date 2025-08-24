package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TiempoEstimadoLlegadaDTO {

    private Long idTiempoEstimado;

    @NotNull(message = "El id de la ruta es obligatorio")
    private Long idRuta;

    @NotNull(message = "El id de la reserva es obligatorio")
    private Long idReserva;

    @NotNull(message = "El tiempo estimado en minutos es obligatorio")
    @Min(value = 0, message = "El tiempo estimado no puede ser negativo")
    private Integer tiempoEstimadoMinutos;

    @NotNull(message = "La fecha/hora de cálculo es obligatoria")
    private LocalDateTime fechaHoraCalculo;
}
