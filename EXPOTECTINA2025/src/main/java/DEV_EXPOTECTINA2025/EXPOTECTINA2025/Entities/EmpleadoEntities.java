package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.util.ArrayList;
import java.util.List;

@GastoEntity
@Table(name = "EMPLEADO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EmpleadoEntities {

    @Id
    @Column(name = "DUIEMPLEADO", unique = true)
    private String duiEmpleado;

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

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    private UserEntity usuario;

    @ManyToOne
    @JoinColumn(name = "IDRANGO", referencedColumnName = "IDRANGO")
    private RangoEntity rango;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<ReservaEntities> reservas = new ArrayList<>();

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SoporteEntity> soportesAtendidos;

    @OneToMany(mappedBy = "duiEmpleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntitesUbicacionEmpleado> ubicacionEmpleados = new ArrayList<>();

}
