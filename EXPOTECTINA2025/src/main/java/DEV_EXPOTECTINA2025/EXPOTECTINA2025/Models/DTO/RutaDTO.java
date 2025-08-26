package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RutaDTO {

    private Long idRuta;

    @NotNull(message = "El id del lugar es obligatorio")
    private Long idLugar;

    @NotBlank(message = "El origen es obligatorio")
    @Size(max = 100, message = "El origen no puede superar los 100 caracteres")
    private String origen;

    @NotBlank(message = "El destino es obligatorio")
    @Size(max = 100, message = "El destino no puede superar los 100 caracteres")
    private String destino;

    @NotNull(message = "La distancia es obligatoria")
    @Digits(integer = 3, fraction = 2, message = "La distancia debe tener hasta 3 enteros y 2 decimales (###.##)")
    private BigDecimal distanciaKm;

    @NotNull(message = "La duración estimada es obligatoria")
    private LocalDateTime duracionEstimada;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 300, message = "La descripción no puede superar los 300 caracteres")
    private String descripcion;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede superar los 50 caracteres")
    private String estado;
}
