package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class MultimediaPaqueteDTO {

    private Long idMultimedia;

    @NotNull(message = "El id del lugar es obligatorio")
    private Long idLugar;

    @Size(max = 255, message = "La URL no puede superar los 255 caracteres")
    private String url;

    @Size(max = 50, message = "El tipo no puede superar los 50 caracteres")
    private String tipo;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;
}
