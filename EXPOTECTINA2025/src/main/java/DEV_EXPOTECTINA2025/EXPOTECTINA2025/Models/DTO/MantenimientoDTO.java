package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@ToString
public class MantenimientoDTO {

    private Long idMantenimiento;

    @NotNull(message = "El id del vehículo es obligatorio")
    private Long idVehiculo;

    @NotNull(message = "El id del gasto es obligatorio")
    private Long idGasto;

    @NotNull(message = "La fecha de mantenimiento es obligatoria")
    private LocalDate fechaMantenimiento;

    @Size(max = 100, message = "El realizador no puede superar los 100 caracteres")
    private String realizador;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;
}
