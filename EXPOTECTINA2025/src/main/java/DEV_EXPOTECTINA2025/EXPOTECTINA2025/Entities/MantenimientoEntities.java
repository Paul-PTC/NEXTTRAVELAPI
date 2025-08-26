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
@Getter @Setter @ToString @EqualsAndHashCode
public class MantenimientoEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mantenimiento")
    @SequenceGenerator(name = "seq_mantenimiento", sequenceName = "SEQ_MANTENIMIENTO", allocationSize = 1)
    @Column(name = "IDMANTENIMIENTO")
    private Long idMantenimiento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDVEHICULO", referencedColumnName = "IDVEHICULO", nullable = false)
    private VehiculosEntities vehiculo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDGASTO", referencedColumnName = "IDGASTO", nullable = false)
    private GastoEntities gasto;

    @Column(name = "FECHAMANTENIMIENTO", nullable = false)
    private LocalDate fechaMantenimiento;

    @Column(name = "REALIZADOR", length = 100)
    private String realizador;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
}
