package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "GASTO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GastoEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GASTO")
    @SequenceGenerator(name = "SEQ_GASTO", sequenceName = "SEQ_GASTO", allocationSize = 1)
    @Column(name = "IDGASTO")
    private Long idGasto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDTIPOGASTO", referencedColumnName = "IDTIPOGASTO", nullable = false)
    private TipoGastoEntities tipoGasto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO", nullable = false)
    private EmpleadoEntities empleado;

    @Column(name = "MONTO", precision = 10, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "FECHAGASTO", nullable = false)
    private LocalDate fechaGasto; // Oracle DATE

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
}

