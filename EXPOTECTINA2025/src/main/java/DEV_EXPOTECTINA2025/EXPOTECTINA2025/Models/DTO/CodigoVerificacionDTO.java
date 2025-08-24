package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CodigoVerificacionDTO {

    private Long idCodigo;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;                // FK → Usuario(idUsuario)

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 10, message = "El código no puede superar 10 caracteres")
    private String codigo;

    @NotNull(message = "La fecha de generación es obligatoria")
    private LocalDateTime fechaGenerado;

    @NotBlank(message = "El estado 'usado' es obligatorio")
    @Pattern(regexp = "^[SN]$", message = "El campo 'usado' debe ser 'S' o 'N'")
    private String usado;                  // 'S' / 'N'
}
