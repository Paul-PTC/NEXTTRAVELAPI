package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RangoDTO {

    private Long idRango;

    @NotBlank(message = "El nombre del rango es obligatorio")
    @Size(max = 50, message = "El nombre del rango no puede superar los 50 caracteres")
    private String nombreRango;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;
}
