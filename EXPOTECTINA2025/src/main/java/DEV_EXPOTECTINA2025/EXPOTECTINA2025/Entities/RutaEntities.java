package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "RUTA",
        uniqueConstraints = @UniqueConstraint(name = "uq_ruta_origen_destino", columnNames = {"ORIGEN","DESTINO"})
)
@Getter @Setter @ToString @EqualsAndHashCode
public class RutaEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ruta")
    @SequenceGenerator(name = "seq_ruta", sequenceName = "SEQ_RUTA", allocationSize = 1)
    @Column(name = "IDRUTA")
    private Long idRuta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDLUGAR", referencedColumnName = "IDLUGAR", nullable = false)
    private LugarTuristicoEntity lugar;

    @Column(name = "ORIGEN", nullable = false, length = 100)
    private String origen;

    @Column(name = "DESTINO", nullable = false, length = 100)
    private String destino;

    @Column(name = "DISTANCIAKM", nullable = false, precision = 5, scale = 2)
    private BigDecimal distanciaKm;

    // Oracle DATE almacena fecha y hora; lo mapeamos como LocalDateTime
    @Column(name = "DURACIONESTIMADA", nullable = false)
    private LocalDateTime duracionEstimada;

    @Column(name = "DESCRIPCION", nullable = false, length = 300)
    private String descripcion;

    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;
}
