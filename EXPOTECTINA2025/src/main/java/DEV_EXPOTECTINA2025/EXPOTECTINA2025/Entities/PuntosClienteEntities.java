package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "PUNTOSCLIENTE")
@Getter @Setter @ToString @EqualsAndHashCode
public class PuntosClienteEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_puntos_cliente")
    @SequenceGenerator(name = "seq_puntos_cliente", sequenceName = "SEQ_PUNTOSCLIENTE", allocationSize = 1)
    @Column(name = "IDPUNTOS")
    private Long idPuntos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUICLIENTE", referencedColumnName = "DUICLIENTE", nullable = false)
    private ClienteEntities cliente;

    @Column(name = "PUNTOSACUMULADOS", nullable = false)
    private Integer puntosAcumulados;

    @Column(name = "PUNTOSCANJEADOS", nullable = false)
    private Integer puntosCanjeados;

    @Column(name = "ULTIMAACTUALIZACION", nullable = false)
    private LocalDateTime ultimaActualizacion;
}

