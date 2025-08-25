package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CollectionId;

@Entity
@Table(name = "TIPOMANTENIMIENTO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TipoMantenimietoEntities {
    @Id
    @SequenceGenerator(name = "seq_tipo_mantenimiento", sequenceName = "SEQ_TIPOMANTENIMIENTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_mantenimiento")
    @Column(name = "IDTIPOMANTENIMIENTO")
    private Long idTipoMantenimiento;

    @Column(name= "NOMBRETIPO")
    private String nombreTipo;

    @Column(name = "DESCRIPCION")
    private String descripcion;
}
