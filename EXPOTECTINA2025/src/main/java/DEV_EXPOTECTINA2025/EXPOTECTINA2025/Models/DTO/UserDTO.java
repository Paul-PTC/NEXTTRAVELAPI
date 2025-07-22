package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class UserDTO {
    //Atributos
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @Positive(message = "El ID del grupo expo debe ser positivo")
    private Long idGrupoExpo;
    @Positive(message = "El ID del rol debe ser positivo")
    private Long idRol;

    @Email(message = "Debe ser un correo valido")
    private String correo;

    @Size(min = 8, message = "La contraseña debe tener 8 caracteres")
    private String contraseña;

    @NotNull(message = "El ID de cargo no puede ser nulo")
    @Positive(message = "El ID de cargo debe ser positivo")
    private Long idCargo;
}

