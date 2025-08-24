package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "CALIFICACION")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CalificacionEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_calificacion")
    @SequenceGenerator(name = "seq_calificacion", sequenceName = "seq_Calificacion", allocationSize = 1)
    @Column(name = "IDCALIFICACION")
    private Long idCalificacion;

    @ManyToOne
    @JoinColumn(name = "IDRESERVA", referencedColumnName = "IDRESERVA")
    private ReservaEntities reserva;

    @Column(name = "CALIFICACION")
    private Integer calificacion;

    @Column(name = "FECHACALIFICACION")
    private LocalDateTime fechaCalificacion;
}

