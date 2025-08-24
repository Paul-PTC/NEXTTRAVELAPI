package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIPOGASTO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TipoGastoEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipogasto")
    @SequenceGenerator(name = "seq_tipogasto", sequenceName = "SEQ_TIPOGASTO", allocationSize = 1)
    @Column(name = "IDTIPOGASTO")
    private Long idTipoGasto;

    @Column(name = "NOMBRETIPO", nullable = false, length = 100, unique = true)
    private String nombreTipo;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
}
