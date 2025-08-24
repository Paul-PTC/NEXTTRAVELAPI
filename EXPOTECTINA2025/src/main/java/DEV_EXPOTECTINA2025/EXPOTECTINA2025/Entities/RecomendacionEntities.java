package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RECOMENDACION")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RecomendacionEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recomendacion")
    @SequenceGenerator(name = "seq_recomendacion", sequenceName = "SEQ_RECOMENDACION", allocationSize = 1)
    @Column(name = "IDRECOMENDACION")
    private Long idRecomendacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUICLIENTE", referencedColumnName = "DUICLIENTE", nullable = false)
    private ClienteEntities cliente; // FK → Cliente(duiCliente)

    @Column(name = "TIPOLUGAR", nullable = false, length = 50)
    private String tipoLugar;

    @Column(name = "LUGARSUGERIDO", nullable = false, length = 100)
    private String lugarSugerido;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
}
