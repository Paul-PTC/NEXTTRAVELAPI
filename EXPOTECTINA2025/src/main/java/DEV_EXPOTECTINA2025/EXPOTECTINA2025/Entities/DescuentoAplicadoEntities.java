package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "DESCUENTOAPLICADO")
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class DescuentoAplicadoEntities {
    //SEQ_DESCUENTOAPLICADO
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DESCUENTOAPLICADO")
    @SequenceGenerator(name = "SEQ_DESCUENTOAPLICADO", sequenceName = "SEQ_DESCUENTOAPLICADO", allocationSize = 1)
    @Column(name = "IDDESCUENTO")
    private Long idDescuento;

    @ManyToOne
    @JoinColumn(name = "IDRESERVA", nullable = false)
    private ReservaEntities reserva;

    @ManyToOne
    @JoinColumn(name = "IDPROMOCION", nullable = false)
    private PromocionEntities promocion;

    @Column(name = "MONTODESCONTADO")
    private String montoDescontado;

    @Column(name = "FECHAAPLICACION")
    private Date fechaAplicacion;

}
