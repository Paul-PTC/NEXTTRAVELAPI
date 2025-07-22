package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CalificacionLugarDTO {

    private Long idCalificacionLugar;

    @NotNull(message = "El ID del lugar es obligatorio")
    private Long idLugar;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private int calificacion;

    private String comentario;

    @NotNull(message = "La fecha de calificación es obligatoria")
    private LocalDateTime fechaCalificacion;
}

