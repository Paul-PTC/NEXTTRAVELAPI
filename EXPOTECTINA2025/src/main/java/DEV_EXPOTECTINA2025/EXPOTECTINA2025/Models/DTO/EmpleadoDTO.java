package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class EmpleadoDTO {

    @NotBlank(message = "El DUI es obligatorio")
    private String duiEmpleado;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID de rango es obligatorio")
    private Long idRango;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Email(message = "Debe ser un correo válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    private String direccion;
    private String fotoPerfil;

    @DecimalMin(value = "0.0", inclusive = true, message = "El salario debe ser positivo")
    private Double salario;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}

