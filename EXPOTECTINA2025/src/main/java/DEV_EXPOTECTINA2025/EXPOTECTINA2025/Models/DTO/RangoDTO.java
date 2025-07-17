package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class RangoDTO {
    @NotBlank(message = "El id es obligatorio")
    private Long id;

    @NotBlank(message = "El nombre del rango es obligatorio")
    private String nombreRango;

    @NotBlank(message = "La descripción del rango es obligatoria")
    private String descripcion;
}
