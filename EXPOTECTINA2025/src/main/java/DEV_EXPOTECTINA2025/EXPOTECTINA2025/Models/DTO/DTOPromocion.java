package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DTOPromocion {


    private Long idPromocion;

    @Size(min = 4, message = "El nombre deberia de constar de minimo 4 caracteres")
    @NotNull(message = "El nombre es Obligatorio")
    private String nombre;

    @Size(min = 4, message = "La descripcion deberia de constar de minimo 4 caracteres")
    @NotNull(message = "La Descripcion es obligatorio")
    private String descripcion;

    @NotNull(message = "EL Procentaje es obligatorio")
    private float porcentaje;


    private Date fechainicio;



    private Date fechafin;

    @Positive(message = "Los puntos deben de ser Positivos")
    @NotNull(message = "Los Puntos Requeridos es obligatorio")
    private Long puntosRequeridos;

    @NotNull(message = "El estado es obligatorio")
    @Size(min = 1, message = "EL minimos de caracteres es 1")
    private String estado;
}
