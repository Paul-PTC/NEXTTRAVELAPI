package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "ESTADOVIAJE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstadoViajeEntities {

    @Id
    @SequenceGenerator(name = "SEQ_ESTADOVIAJE", sequenceName = "SEQ_ESTADOVIAJE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTADOVIAJE")
    @Column(name = "IDESTADOVIAJE")
    private Long idEstadoViaje;

    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;

    @Column(name = "FECHAESTADO", nullable = false)
    private LocalDateTime fechaEstado;

    @Column(name = "OBSERVACION", length = 200)
    private String observacion;

    // Relación con Reserva (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "idreserva", nullable = false,
            foreignKey = @ForeignKey(name = "fk_estadoviaje_reserva"))
    private ReservaEntities reserva;
}
