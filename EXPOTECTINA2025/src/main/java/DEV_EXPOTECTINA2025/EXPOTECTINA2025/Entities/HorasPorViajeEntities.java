package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "HorasPorViaje")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class HorasPorViajeEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "horas_viaje_seq")
    @SequenceGenerator(name = "horas_viaje_seq", sequenceName = "seq_horas_viaje", allocationSize = 1)
    @Column(name = "IDHORASVIAJE")
    private Long idHorasViaje;

    @ManyToOne
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO")
    private EmpleadoEntities empleado;

    @ManyToOne
    @JoinColumn(name = "IDVEHICULO", referencedColumnName = "IDVEHICULO")
    private VehiculosEntities idVehiculo;

    @ManyToOne
    @JoinColumn(name = "IDRUTA", referencedColumnName = "IDRUTA")
    private RutaEntities idRuta;

    @Column(name = "FECHA")
    private Date fechaViaje;

    @Column(name = "HORAINICIO")
    private LocalDateTime horaLlegada;

    @Column(name = "HORAFIN")
    private LocalDateTime horaSalida;

    @Column(name = "DURACION")
    private String duracion;
}
