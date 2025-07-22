package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "Vehiculo")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VehiculosEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVehiculo")
    private Integer idVehiculo;
    @Column(name = "placa", length = 10, nullable = false, unique = true)
    private String placa;

    @Column(name = "marca", length = 50)
    private String marca;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "fechaVencimientoCirculacion")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimientoCirculacion;

    @Column(name = "fechaVencimientoSeguro")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimientoSeguro;

    @Column(name = "fechaVencimientoRevision")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimientoRevision;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private EmpleadoEntities empleado;

    // 🔹 Relación con Ruta
    @ManyToOne
    @JoinColumn(name = "id_ruta", nullable = false)
    private RutaEntities ruta;

}
