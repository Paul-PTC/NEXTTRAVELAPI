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
    @Column(name = "IDUSUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    private Long id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "IDGRUPOEXPO")
    private Long idGrupoExpo;
    @Column(name = "IDROL")
    private Long idRol;
    @Column(name = "CORREO", unique = true)
    private String correo;
    @Column(name = "CONTRASENA")
    private String contraseña;
    @Column(name = "IDCARGO")
    private Long idCargo;
}
