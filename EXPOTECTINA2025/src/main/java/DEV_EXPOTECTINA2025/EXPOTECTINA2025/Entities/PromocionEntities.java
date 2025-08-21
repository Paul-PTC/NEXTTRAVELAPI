package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "PROMOCION")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PromocionEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROMOCION")
    @SequenceGenerator(name = "SEQ_PROMOCION", sequenceName = "SEQ_PROMOCION", allocationSize = 1)
    @Column(name = "IDPROMOCION")
    private Long idPromocion;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PORCENTAJE")
    private float porcentaje;

    @Column(name = "FECHAINICIO")
    private Date fechainicio;

    @Column(name = "FECHAFIN")
    private Date fechafin;

    @Column(name = "PUNTOSREQUERIDOS")
    private Long puntosRequeridos;

    @Column(name = "ESTADO")
    private String estado;

}
