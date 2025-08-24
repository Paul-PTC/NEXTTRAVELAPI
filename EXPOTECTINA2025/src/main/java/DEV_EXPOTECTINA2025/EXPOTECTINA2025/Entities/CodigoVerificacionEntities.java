package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "CODIGOVERIFICACION")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CodigoVerificacionEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cod_ver")
    @SequenceGenerator(name = "seq_cod_ver", sequenceName = "SEQ_CODIGOVERIFICACION", allocationSize = 1)
    @Column(name = "IDCODIGO")
    private Long idCodigo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO", nullable = false)
    private UserEntity usuario; // FK → Usuario

    @Column(name = "CODIGO", length = 10, nullable = false)
    private String codigo;

    @Column(name = "FECHAGENERADO", nullable = false)
    private LocalDateTime fechaGenerado;

    @Column(name = "USADO", length = 1, nullable = false)
    private String usado; // 'S' o 'N'
}
