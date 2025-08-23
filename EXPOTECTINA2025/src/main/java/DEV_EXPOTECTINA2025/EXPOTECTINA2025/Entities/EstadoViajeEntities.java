package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@GastoEntity
@Table(name = "EstadoViaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstadoViajeEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstadoViaje")
    private Long idEstadoViaje;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "fechaEstado", nullable = false)
    private LocalDateTime fechaEstado;

    @Column(name = "observacion", length = 200)
    private String observacion;

    // Relación con Reserva (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "idReserva", nullable = false,
            foreignKey = @ForeignKey(name = "fk_estadoviaje_reserva"))
    private ReservaEntities reserva;
}
