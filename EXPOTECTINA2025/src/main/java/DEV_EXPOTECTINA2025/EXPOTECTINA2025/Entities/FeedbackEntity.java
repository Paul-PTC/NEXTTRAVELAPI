package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "FEEDBACK")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Feedback")
    @SequenceGenerator(name = "seq_Feedback", sequenceName = "seq_Feedback", allocationSize = 1)
    @Column(name = "IDFEEDBACK")
    private Long idFeedback;

    @Column(name = "COMENTARIO", length = 255, nullable = false)
    private String comentario;

    @Column(name = "FECHACOMENTARIO", nullable = false)
    private LocalDateTime fechaComentario;

    // Relación ManyToOne con Reserva
    @ManyToOne
    @JoinColumn(name = "IDRESERVA", referencedColumnName = "IDRESERVA")
    private ReservaEntities reserva;
}