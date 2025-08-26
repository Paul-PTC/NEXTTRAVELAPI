package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "RUTASGUARDADAS")
@Getter @Setter @ToString @EqualsAndHashCode
public class RutasGuardadasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_rutas_guardadas")
    @SequenceGenerator(name = "seq_rutas_guardadas", sequenceName = "seq_RutasGuardadas", allocationSize = 1)
    @Column(name = "IDRUTAGUARDADA")
    private Long idRutaGuardada;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUICLIENTE", referencedColumnName = "DUICLIENTE", nullable = false)
    private ClienteEntities cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDRUTA", referencedColumnName = "IDRUTA", nullable = false)
    private RutaEntities ruta;

    @Column(name = "NOMBRERUTA", nullable = false, length = 100)
    private String nombreRuta;

    @Column(name = "FECHAGUARDADO", nullable = false)
    private LocalDateTime fechaGuardado;
}
