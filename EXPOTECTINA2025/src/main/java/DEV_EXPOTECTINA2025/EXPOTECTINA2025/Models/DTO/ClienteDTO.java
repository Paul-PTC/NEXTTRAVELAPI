package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class ClienteDTO {
    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "El género es obligatorio")
    private String genero;

    @NotNull(message = "La edad es obligatoria")
    private Integer edad;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    private String direccion; // Si es opcional, no necesita anotación

    @NotNull(message = "La fecha de registro es obligatoria")
    private Date fechaRegistro;

    private String fotoPerfil; // Si es opcional, no necesita anotación

}
