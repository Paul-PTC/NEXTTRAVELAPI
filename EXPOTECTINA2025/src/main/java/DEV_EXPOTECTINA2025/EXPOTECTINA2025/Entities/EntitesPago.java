package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;


@EqualsAndHashCode
@Getter
@Setter
@Table(name = "PAGO")
@ToString
@Entity
public class EntitesPago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAGO")
    @SequenceGenerator(name = "SEQ_PAGO", sequenceName = "SEQ_PAGO", allocationSize = 1)
    @Column(name = "IDPAGO")
    private Long idPago;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDRESERVA", referencedColumnName = "IDRESERVA")
    private Long idReserva;

    @Column(name = "MONTO")
    private float Monto;

    @Column(name = "FECHAPAGO")
    private Date fechaPago;

    @Column(name = "ESTADO")
    private String estado;
}
