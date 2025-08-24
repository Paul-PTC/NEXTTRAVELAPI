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
    @Column(name = "DUICLIENTE", length = 10)
    @NotBlank(message = "El DUI es obligatorio")
    private String duiCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "APELLIDO", length = 50, nullable = false)
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(name = "TELEFONO", length = 20, nullable = false)
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Column(name = "CORREO", length = 100, nullable = false, unique = true)
    private String correo;

    @Column(name = "GENERO", length = 15)
    private String genero;

    @Min(value = 0, message = "La edad debe ser un número positivo")
    @Column(name = "EDAD")
    private Integer edad;

    @NotBlank(message = "El estado es obligatorio")
    @Column(name = "ESTADO", length = 20, nullable = false)
    private String estado;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @NotNull(message = "La fecha de registro es obligatoria")
    @Column(name = "FECHAREGISTRO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Column(name = "FOTOPERFIL", length = 255)
    private String fotoPerfil;

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private UserEntity usuario;
}
