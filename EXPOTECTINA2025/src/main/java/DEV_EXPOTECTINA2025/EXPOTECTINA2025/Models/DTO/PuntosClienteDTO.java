package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PuntosClienteDTO {

    private Long idPuntos;

    @NotBlank(message = "El DUI del cliente es obligatorio")
    @Size(max = 10, message = "El DUI no puede superar los 10 caracteres")
    private String duiCliente;    // FK a Cliente

    @NotNull(message = "Los puntos acumulados son obligatorios")
    @Min(value = 0, message = "Los puntos acumulados no pueden ser negativos")
    private Integer puntosAcumulados;

    @NotNull(message = "Los puntos canjeados son obligatorios")
    @Min(value = 0, message = "Los puntos canjeados no pueden ser negativos")
    private Integer puntosCanjeados;

    @NotNull(message = "La fecha de última actualización es obligatoria")
    private LocalDateTime ultimaActualizacion;
}

