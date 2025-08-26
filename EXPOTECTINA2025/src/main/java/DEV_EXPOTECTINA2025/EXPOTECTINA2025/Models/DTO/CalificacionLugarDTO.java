package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CalificacionLugarDTO {

    private Long idCalificacionLugar;

    @NotNull(message = "El id del lugar es obligatorio")
    private Long idLugar;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer calificacion;

    @Size(max = 250, message = "El comentario no puede superar los 250 caracteres")
    private String comentario;

    @NotNull(message = "La fecha de calificación es obligatoria")
    private LocalDateTime fechaCalificacion;
}

