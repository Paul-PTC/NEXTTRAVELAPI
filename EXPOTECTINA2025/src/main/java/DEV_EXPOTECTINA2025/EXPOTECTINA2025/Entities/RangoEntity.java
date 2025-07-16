package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RANGOEMPLEADO") // Corregido según tu tabla real
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RangoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rango")
    @SequenceGenerator(name = "seq_rango", sequenceName = "seq_rango", allocationSize = 1)
    @Column(name = "IDRANGO")
    private Long id;

    @Column(name = "NOMBRERANGO")
    private String nombreRango;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Se mapea que la relación apuntará hacia el atributo rango de EmpleadoEntities
     * ya que se podrá tener un rango en muchos empleados.
     */
    @OneToMany(mappedBy = "rango", cascade = CascadeType.ALL)
    private List<EmpleadoEntities> empleados = new ArrayList<>();
}
