package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;


@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
public class DTOPromocion {

    private Long idPromocion;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El porcentaje es obligatorio")
    @Digits(integer = 3, fraction = 2, message = "Porcentaje con hasta 3 enteros y 2 decimales")
    private BigDecimal porcentaje;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime fechaFin;

    @Min(value = 0, message = "Los puntos requeridos no pueden ser negativos")
    private Integer puntosRequeridos;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20, message = "El estado no puede superar los 20 caracteres")
    private String estado;
}