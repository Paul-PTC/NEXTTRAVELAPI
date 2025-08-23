package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MantenimientoDTO {
    @NotNull(message = "El idMantenimiento es obligatorio")
    private Long idMantenimiento;

    @NotNull(message = "El idVehiculo es obligatorio")
    private Long idVehiculo;

    @NotNull(message = "El idGasto es obligatorio")
    private Long idGasto;

    @NotNull(message = "La fecha de mantenimiento es obligatoria")
    private LocalDateTime fechaMantenimiento;

    @NotBlank(message = "El realizador es obligatorio")
    private String realizador;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

}
