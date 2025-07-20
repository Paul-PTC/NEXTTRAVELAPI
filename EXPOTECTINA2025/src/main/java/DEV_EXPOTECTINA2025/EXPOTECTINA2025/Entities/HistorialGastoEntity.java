package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "HISTORIALGASTO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class HistorialGastoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_historial_gasto")
    @SequenceGenerator(name = "seq_historial_gasto", sequenceName = "SEQ_ID_HISTORIAL_GASTO", allocationSize = 1)
    @Column(name = "IDHISTORIALGASTO")
    private Long idHistorialGasto;

    @ManyToOne
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO")
    private EmpleadoEntities empleado;

    @Column(name = "FECHAGASTO")
    private LocalDate fechaGasto;

    @Column(name = "TOTALGASTO")
    private Double totalGasto;

    @Column(name = "OBSERVACIONES")
    private String observaciones;
}

