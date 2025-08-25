package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(
        name = "VEHICULO",
        uniqueConstraints = @UniqueConstraint(name = "uq_vehiculo_placa", columnNames = "PLACA")
)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VehiculosEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vehiculo")
    @SequenceGenerator(name = "seq_vehiculo", sequenceName = "SEQ_VEHICULO", allocationSize = 1)
    @Column(name = "IDVEHICULO")
    private Long idVehiculo;

    @Column(name = "PLACA", nullable = false, length = 10, unique = true)
    private String placa;

    @Column(name = "MARCA", length = 50)
    private String marca;

    @Column(name = "MODELO", length = 50)
    private String modelo;

    @Column(name = "ANIO")
    private Integer anio;

    @Column(name = "FECHAVENCIMIENTOCIRCULACION")
    private LocalDate fechaVencimientoCirculacion;

    @Column(name = "FECHAVENCIMIENTOSEGURO")
    private LocalDate fechaVencimientoSeguro;

    @Column(name = "FECHAVENCIMIENTOREVISION")
    private LocalDate fechaVencimientoRevision;
}
