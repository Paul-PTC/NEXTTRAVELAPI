package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@EqualsAndHashCode
@Getter
@Table(name = "TIEMPOESTIMADOLLEGADA")
@Setter
@Entity
public class EntitesTiempoLlegada {

    //SEQ_TIEMPOESTIMADO
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIEMPOESTIMADO")
    @SequenceGenerator(name = "SEQ_TIEMPOESTIMADO", sequenceName = "SEQ_TIEMPOESTIMADO", allocationSize = 1)
    @Column(name = "IDTIEMPOESTIMADO")
    private Long idTiempoestimado;

    @Column(name = "IDRUTA")
    private Long idRuta;

    @Column(name = "IDRESERVA")
    private Long idReserva;

    @Column(name = "TIEMPOESTIMADOMINUTOS")
    private Long tiempoEstimado; //Nos quedamos con Long o con Float?

    @Column(name = "FECHAHORACALCULO")
    private Date fechahoraCalculo;
}
