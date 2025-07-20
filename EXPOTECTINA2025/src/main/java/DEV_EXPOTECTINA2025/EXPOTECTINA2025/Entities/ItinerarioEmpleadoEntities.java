package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "ITINERARIOEMPLEADO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ItinerarioEmpleadoEntities {
    @Id
    @Column(name = "idItenerario")
    private Long idItinerario;

    @Column(name = "duiEmpleado")
    private String duiEmpleado;

    @Column(name = "idVehiculo")
    private Long idVehiculo;

    @Column(name = "idRuta")
    private Long idRuta;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "horaInicio")
    private Date horaInicio;

    @Column(name = "horaFin")
    private Date horaFin;

    @Column(name = "Observaciones")
    private String Observaciones;
}
