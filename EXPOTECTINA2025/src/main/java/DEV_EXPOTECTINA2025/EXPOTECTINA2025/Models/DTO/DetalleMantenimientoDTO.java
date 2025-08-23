package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DetalleMantenimientoDTO {

    @NotNull(message = "El idDetalleMantenimiento es obligatorio")
    private Long idDetalleMantenimiento;

    @NotNull(message = "El idTipoMantenimiento es obligatorio")
    private Long idTipoMantenimiento;

    @NotNull(message = "El idMantenimiento es obligatorio")
    private Long idMantenimiento;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
}
