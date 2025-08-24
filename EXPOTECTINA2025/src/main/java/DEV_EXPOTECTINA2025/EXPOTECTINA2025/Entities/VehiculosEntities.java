package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "VEHICULO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VehiculosEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Vehiculo")
    @SequenceGenerator(name = "seq_Vehiculo", sequenceName = "seq_Vehiculo", allocationSize = 1)
    @Column(name = "IDVEHICULO")
    private Integer idVehiculo;

    @Column(name = "PLACA", length = 10, nullable = false, unique = true)
    private String placa;

    @Column(name = "MARCA", length = 50)
    private String marca;

    @Column(name = "MODELO", length = 50)
    private String modelo;

    @Column(name = "ANIO")
    private Integer anio;

    @Column(name = "FECHAVENCIMIENTOCIRCULACION")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimientoCirculacion;

    @Column(name = "FECHAVENCIMIENTOSEGURO")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimientoSeguro;

    @Column(name = "FECHAVENCIMIENTOREVISION")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimientoRevision;

}
