package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "DETALLEPAGO")
@Getter
@Setter
public class DetallePagoEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLEPAGO")
    @SequenceGenerator(name = "SEQ_DETALLEPAGO", sequenceName = "SEQ_DETALLEPAGO", allocationSize = 1)
    @Column(name = "IDDETALLEPAGO")
    private long idDetallePago;

    @Column(name = "IDPAGO")
    private long idPago;

    @Column(name = "METODO")
    private String metodo;

    @Column(name = "REFERENCIA")
    private String referencia;

    @Column(name = "OBSERVACION")
    private String observacion;
}
