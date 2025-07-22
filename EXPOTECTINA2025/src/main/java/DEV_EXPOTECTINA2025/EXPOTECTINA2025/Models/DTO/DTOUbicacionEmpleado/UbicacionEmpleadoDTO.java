package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOUbicacionEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
public class UbicacionEmpleadoDTO {


    private  Long idBitacora;
    @NotBlank(message = "El dui es Obligatorio")
    private EmpleadoEntities duiEmpleado;
    @NotBlank(message = "la ubicacion es Obligatoria")
    private String ubicacion;

    private String fecharegistro;
}
