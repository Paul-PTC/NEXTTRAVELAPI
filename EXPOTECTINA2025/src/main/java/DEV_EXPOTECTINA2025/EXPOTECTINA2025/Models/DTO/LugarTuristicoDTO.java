package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
public class LugarTuristicoDTO {

    private Long idLugar;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 50, message = "El tipo de lugar no puede superar los 50 caracteres")
    private String tipoLugar;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;

    @NotNull(message = "La latitud es obligatoria")
    @Digits(integer = 10, fraction = 6, message = "Latitud con hasta 10 enteros y 6 decimales")
    private BigDecimal latitud;

    @NotNull(message = "La longitud es obligatoria")
    @Digits(integer = 10, fraction = 6, message = "Longitud con hasta 10 enteros y 6 decimales")
    private BigDecimal longitud;
}
