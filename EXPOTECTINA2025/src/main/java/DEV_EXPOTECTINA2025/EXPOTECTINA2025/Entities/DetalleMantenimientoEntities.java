package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DETALLEMANTENIMIENTO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DetalleMantenimientoEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_mantenimiento_seq")
    @SequenceGenerator(name = "detalle_mantenimiento_seq", sequenceName = "SEQ_DETALLEMANTENIMIENTO", allocationSize = 1)
    @Column(name = "IDDETALLEMANTENIMIENTO")
    private Integer idDetalleMantenimiento;

    @Column(name = "IDTIPOMANTENIMIENTO", nullable = false)
    private Integer idTipoMantenimiento;

    @Column(name = "IDMANTENIMIENTO", nullable = false)
    private Integer idMantenimiento;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;
}

