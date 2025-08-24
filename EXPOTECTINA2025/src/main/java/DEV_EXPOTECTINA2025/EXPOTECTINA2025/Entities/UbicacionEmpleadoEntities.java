package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "UBICACIONEMPLEADO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UbicacionEmpleadoEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ubicacion_empleado")
    @SequenceGenerator(name = "seq_ubicacion_empleado", sequenceName = "SEQ_UBICACIONEMPLEADO", allocationSize = 1)
    @Column(name = "IDUBICACIONEMPLEADO")
    private Long idUbicacionEmpleado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO", nullable = false)
    private EmpleadoEntities empleado; // FK → Empleado(duiEmpleado)

    @Column(name = "LATITUD", nullable = false, precision = 10, scale = 6)
    private BigDecimal latitud;

    @Column(name = "LONGITUD", nullable = false, precision = 10, scale = 6)
    private BigDecimal longitud;

    @Column(name = "FECHAHORAREGISTROE", nullable = false)
    private LocalDateTime fechaHoraRegistroE;
}
