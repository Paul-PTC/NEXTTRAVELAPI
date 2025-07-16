package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "EMPLEADO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EmpleadoEntities {

    @Id
    @Column(name = "DUIEMPLEADO")
    private String duiEmpleado;

    @Column(name = "IDUSUARIO", nullable = false)
    private Long idUsuario;

    @Column(name = "IDRANGO", nullable = false)
    private Long idRango;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "CORREO", unique = true)
    private String correo;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "FOTOPERFIL")
    private String fotoPerfil;

    @Column(name = "SALARIO")
    private Double salario;

    @Column(name = "ESTADO")
    private String estado;

    /**
     * Se define que el atributo usuario es de tipo UserEntity y que este campo
     * con JoinColumn(name -> apunta hacia la llave foránea
     * referencedColumnName -> apunta hacia la llave primaria de la tabla TBUSUARIO
     */
    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    private UserEntity usuario;

    /**
     * Se define que el atributo rango es de tipo RangoEntity y que este campo
     * con JoinColumn(name -> apunta hacia la llave foránea
     * referencedColumnName -> apunta hacia la llave primaria de la tabla RANGO
     */
    @ManyToOne
    @JoinColumn(name = "IDRANGO", referencedColumnName = "IDRANGO")
    private RangoEntity rango;
}
