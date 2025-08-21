package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DTOUbicacionempleado {

    private  Long idUbicacionEmpleado;

    @ManyToOne
    @NotBlank(message = "El DUI es Obligatorio.")
    private EmpleadoEntities duiEmpleado;

    @NotBlank(message = "La ubicacion es Obligatoria")
    private String ubicacionEmpleado;

    @NotNull(message = "la fecha del Registro es Obligaotria")
    private LocalDateTime fechaRegistro;

}
