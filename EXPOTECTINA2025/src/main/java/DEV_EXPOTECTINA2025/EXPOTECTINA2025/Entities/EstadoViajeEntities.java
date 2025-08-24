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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_viaje_seq")
    @SequenceGenerator(name = "estado_viaje_seq", sequenceName = "SEQ_ESTADO_VIAJE", allocationSize = 1)
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
    @JoinColumn(name = "IDRESERVA", nullable = false,
            foreignKey = @ForeignKey(name = "fk_estadoviaje_reserva"))
    private ReservaEntities reserva;
}
