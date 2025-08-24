package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "GANANCIA")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GananciaEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ganancia")
    @SequenceGenerator(name = "seq_ganancia", sequenceName = "SEQ_GANANCIA", allocationSize = 1)
    @Column(name = "IDGANANCIA")
    private Long idGanancia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO", nullable = false)
    private EmpleadoEntities empleado;   // FK → Empleado(duiEmpleado)

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDRESERVA", referencedColumnName = "IDRESERVA", nullable = false)
    private ReservaEntities reserva;     // FK → Reserva(idReserva)

    @Column(name = "FECHAGANANCIA", nullable = false)
    private LocalDate fechaGanancia;     // Oracle DATE

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "MONTO", precision = 10, scale = 2, nullable = false)
    private BigDecimal monto;
}
