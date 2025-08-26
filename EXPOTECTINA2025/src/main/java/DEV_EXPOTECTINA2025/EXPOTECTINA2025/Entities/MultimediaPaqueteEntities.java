package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MULTIMEDIAPAQUETE")
@Getter @Setter @ToString @EqualsAndHashCode
public class MultimediaPaqueteEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_multimedia")
    @SequenceGenerator(name = "seq_multimedia", sequenceName = "seq_Multimedia", allocationSize = 1)
    @Column(name = "IDMULTIMEDIA")
    private Long idMultimedia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDLUGAR", referencedColumnName = "IDLUGAR", nullable = false)
    private LugarTuristicoEntity lugar;

    @Column(name = "URL", length = 255)
    private String url;

    @Column(name = "TIPO", length = 50)
    private String tipo;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
}
