package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class FeedbackDTO {

    private Long idFeedback;

    @NotNull(message = "El ID de la reserva no puede ser nulo")
    @Positive(message = "El ID de la reserva debe ser un número positivo")
    private Long idReserva;

    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 255, message = "El comentario no puede tener más de 255 caracteres")
    private String comentario;

    @NotNull(message = "La fecha del comentario es obligatoria")
    private LocalDateTime fechaComentario;

}
