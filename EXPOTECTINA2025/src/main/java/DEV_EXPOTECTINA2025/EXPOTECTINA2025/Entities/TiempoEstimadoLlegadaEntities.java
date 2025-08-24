package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "TIEMPOESTIMADOLLEGADA")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TiempoEstimadoLlegadaEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tiempo_estimado")
    @SequenceGenerator(name = "seq_tiempo_estimado", sequenceName = "SEQ_TIEMPOESTIMADOLLEGADA", allocationSize = 1)
    @Column(name = "IDTIEMPOESTIMADO")
    private Long idTiempoEstimado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDRUTA", referencedColumnName = "IDRUTA", nullable = false)
    private RutaEntities ruta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDRESERVA", referencedColumnName = "IDRESERVA", nullable = false)
    private ReservaEntities reserva;

    @Column(name = "TIEMPOESTIMADOMINUTOS", nullable = false)
    private Integer tiempoEstimadoMinutos;

    @Column(name = "FECHAHORACALCULO", nullable = false)
    private LocalDateTime fechaHoraCalculo;
}
