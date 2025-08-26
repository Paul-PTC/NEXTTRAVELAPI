package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PROMOCION")
@Getter @Setter @ToString @EqualsAndHashCode
public class PromocionEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_promocion")
    @SequenceGenerator(name = "seq_promocion", sequenceName = "seq_Promocion", allocationSize = 1)
    @Column(name = "IDPROMOCION")
    private Long idPromocion;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;

    @Column(name = "PORCENTAJE", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @Column(name = "FECHAINICIO", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "FECHAFIN", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "PUNTOSREQUERIDOS")
    private Integer puntosRequeridos;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;
}
