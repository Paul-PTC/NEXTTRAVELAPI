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

    private Long idTiempoestimado;

    @NotNull(message = "El id de la ruta es obligatorio")
    private Long idRuta;

    @NotNull(message = "El id de la reserva es obligatorio")
    private Long idReserva;

    @NotNull(message = "El tiempo estimado en minutos es obligatorio")
    private Integer tiempoEstimado;

    @NotNull(message = "La Fecha es obligatorio")
    private LocalDateTime fechaHoraCalculo;

    public LocalDateTime getFechaHoraCalculo() {
        return fechaHoraCalculo;
    }

    public void setFechaHoraCalculo(LocalDateTime fechaHoraCalculo) {
        this.fechaHoraCalculo = fechaHoraCalculo;
    }
}
