package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
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
    @NotBlank(message = "El DUI es obligatorio")
    private String duiCliente;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correo;

    private String genero;

    @Min(value = 0, message = "La edad debe ser un número positivo")
    private Integer edad;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    private String direccion;

    @NotNull(message = "La fecha de registro es obligatoria")
    private Date fechaRegistro;

    private String fotoPerfil;

}
