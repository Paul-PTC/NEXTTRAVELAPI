package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MANTENIMIENTO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MantenimientoEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // o SEQUENCE si usas secuencia en Oracle
    @Column(name = "IDMANTENIMIENTO")
    private Long idMantenimiento;

    @Column(name = "IDVEHICULO", nullable = false)
    private Long idVehiculo;

    @Column(name = "IDGASTO", nullable = false)
    private Long idGasto;

    @Column(name = "FECHAMANTENIMIENTO", nullable = false)
    private LocalDate fechaMantenimiento;

    @Column(name = "REALIZADOR", length = 100)
    private String realizador;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;
}
