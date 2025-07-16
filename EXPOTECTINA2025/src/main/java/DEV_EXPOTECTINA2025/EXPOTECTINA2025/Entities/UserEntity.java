package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USUARIO") // Corregido según tu tabla real
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "IDUSUARIO")
    private Long id;

    @Column(name = "USUARIO", unique = true)
    private String usuario;

    @Column(name = "CORREO", unique = true)
    private String correo;

    @Column(name = "CONTRASENA")
    private String contrasena;

    @Column(name = "ROL")
    private String rol;

    /**
     * Se mapea que la relación apuntará hacia el atributo usuario de EmpleadoEntities
     * ya que se podrá tener un usuario en muchos empleados.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<EmpleadoEntities> empleados = new ArrayList<>();
}
