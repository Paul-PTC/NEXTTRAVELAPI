package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class TipoMantenimientoDTO {
    @NotNull(message = "El id de Tipo Mantenimiento es obligatorio")
    private Long idTipoMantenimiento;

    @NotBlank(message = "El Nombre del tipo de mantenimiento es obligatorio")
    private String nombreTipo;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

}
