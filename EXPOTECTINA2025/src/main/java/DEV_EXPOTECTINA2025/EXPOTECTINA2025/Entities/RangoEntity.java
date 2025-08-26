package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "RANGOEMPLEADO",
        uniqueConstraints = @UniqueConstraint(name = "uq_rango_nombre", columnNames = "NOMBRERANGO")
)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RangoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rango_empleado")
    @SequenceGenerator(name = "seq_rango_empleado", sequenceName = "seq_RangoEmpleado", allocationSize = 1)
    @Column(name = "IDRANGO")
    private Long idRango;

    @Column(name = "NOMBRERANGO", nullable = false, length = 50, unique = true)
    private String nombreRango;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
}
