package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@GastoEntity
@Table(name = "ITINERARIOEMPLEADO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ItinerarioEmpleadoEntities {
    @Id
    @Column(name = "IDITINERARIO")
    private Long idItinerario;

    @ManyToOne
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO")
    public EmpleadoEntities duiEmpleado;

    @ManyToOne
    @JoinColumn(name = "IDVEHICULO", referencedColumnName = "IDVEHICULO")
    private VehiculosEntities vehiculo;

    @ManyToOne
    @JoinColumn(name = "IDRUTA", referencedColumnName = "IDRUTA")
    private RutaEntities idRuta;

    @Column(name = "FECHA")
    private Date fecha;

    @Column(name = "HORAINICIO")
    private Date horaInicio;

    @Column(name = "HORAFIN")
    private Date horaFin;

    @Column(name = "OBSERVACIONES")
    private String Observaciones;
}
