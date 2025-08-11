package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstadoViajeDTO {

    private Long idEstadoViaje;

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long idReserva;

    @NotBlank(message = "El estado del viaje es obligatorio")
    @Size(max = 50, message = "El estado no puede tener más de 50 caracteres")
    private String estado;

    @NotNull(message = "La fecha del estado es obligatoria")
    private LocalDateTime fechaEstado;

    @Size(max = 200, message = "La observación no puede tener más de 200 caracteres")
    private String observacion;
}
