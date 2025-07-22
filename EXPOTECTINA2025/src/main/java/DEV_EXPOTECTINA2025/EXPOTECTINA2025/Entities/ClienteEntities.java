package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClienteEntities {
    @Id
    @Column(name = "duiCliente", length = 10)
    @NotBlank(message = "El DUI es obligatorio")
    private String duiCliente;

    @NotNull(message = "El ID de usuario es obligatorio")
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(name = "telefono", length = 20, nullable = false)
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Column(name = "correo", length = 100, nullable = false, unique = true)
    private String correo;

    @Column(name = "genero", length = 15)
    private String genero;

    @Min(value = 0, message = "La edad debe ser un número positivo")
    @Column(name = "edad")
    private Integer edad;

    @NotBlank(message = "El estado es obligatorio")
    @Column(name = "estado", length = 20, nullable = false)
    private String estado;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @NotNull(message = "La fecha de registro es obligatoria")
    @Column(name = "fechaRegistro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Column(name = "fotoPerfil", length = 255)
    private String fotoPerfil;

}
