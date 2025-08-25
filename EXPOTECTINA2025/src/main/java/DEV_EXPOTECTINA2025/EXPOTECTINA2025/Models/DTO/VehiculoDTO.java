package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehiculoDTO {

    private Long idVehiculo;

    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 10, message = "La placa no puede superar los 10 caracteres")
    private String placa;

    @Size(max = 50, message = "La marca no puede superar los 50 caracteres")
    private String marca;

    @Size(max = 50, message = "El modelo no puede superar los 50 caracteres")
    private String modelo;

    @Min(value = 1900, message = "El año no puede ser menor a 1900")
    @Max(value = 2100, message = "El año no puede ser mayor a 2100")
    private Integer anio;

    private LocalDate fechaVencimientoCirculacion;
    private LocalDate fechaVencimientoSeguro;
    private LocalDate fechaVencimientoRevision;
}


