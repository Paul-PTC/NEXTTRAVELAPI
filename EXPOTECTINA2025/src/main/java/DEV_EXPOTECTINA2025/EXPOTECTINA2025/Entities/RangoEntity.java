package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RANGOEMPLEADO")
@Getter
@Setter
@ToString(exclude = "empleados") // Excluimos la lista para evitar recursión infinita
@EqualsAndHashCode(exclude = "empleados") // Igual para equals y hashCode
public class RangoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rango_empleado")
    @SequenceGenerator(name = "seq_rango_empleado", sequenceName = "SEQ_RANGOEMPLEADO", allocationSize = 1)
    @Column(name = "IDRANGO")
    private Long id;

    @Column(name = "NOMBRERANGO")
    private String nombreRango;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @OneToMany(mappedBy = "rango", cascade = CascadeType.ALL)
    private List<EmpleadoEntities> empleados = new ArrayList<>();
}
