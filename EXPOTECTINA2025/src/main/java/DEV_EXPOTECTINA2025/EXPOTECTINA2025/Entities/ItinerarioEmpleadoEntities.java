package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "ITINERARIOEMPLEADO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ItinerarioEmpleadoEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITINERARIOEMP")
    @SequenceGenerator(name = "SEQ_ITINERARIOEMP", sequenceName = "SEQ_ITINERARIOEMP", allocationSize = 1)
    @Column(name = "IDITINERARIO")
    private Long idItinerario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO", nullable = false)
    private EmpleadoEntities empleado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDVEHICULO", referencedColumnName = "IDVEHICULO", nullable = false)
    private VehiculosEntities vehiculo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDRUTA", referencedColumnName = "IDRUTA", nullable = false)
    private RutaEntities ruta;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Column(name = "HORAINICIO")
    private LocalTime horaInicio;

    @Column(name = "HORAFIN")
    private LocalTime horaFin;

    @Column(name = "OBSERVACIONES", length = 200)
    private String observaciones;
}
