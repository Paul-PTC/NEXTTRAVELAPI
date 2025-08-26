package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HORASPORVIAJE")
@Getter @Setter @ToString @EqualsAndHashCode
public class HorasPorViajeEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_horas_viaje")
    @SequenceGenerator(name = "seq_horas_viaje", sequenceName = "seq_HorasPorViaje", allocationSize = 1)
    @Column(name = "IDHORASVIAJE")
    private Long idHorasViaje;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO", nullable = false)
    private EmpleadoEntities empleado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDRUTA", referencedColumnName = "IDRUTA", nullable = false)
    private RutaEntities ruta;

    @Column(name = "FECHAVIAJE")
    private LocalDate fechaViaje;            // Oracle DATE

    @Column(name = "HORASALIDA")
    private LocalDateTime horaSalida;        // TIMESTAMP

    @Column(name = "HORALLEGADA")
    private LocalDateTime horaLlegada;       // TIMESTAMP

    @Column(name = "DURACION", length = 20)
    private String duracion;                 // ej. "02:35" o "2h 35m"
}
