package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESERVA")
@Getter
@Setter
public class ReservaEntities {
    @Id
    @Column(name = "idreserva")
    private Long id;

    @Column(name = "DUIEMPLEADO", nullable = false, length = 10)
    private String duiEmpleado;

    @Column(name = "IDRUTA", nullable = false)
    private Long idRuta;

    @Column(name = "FECHARESERVA", nullable = false)
    private LocalDateTime  fechaReserva;

    @Column(name = "FECHAVIAJE", nullable = false)
    private LocalDateTime fechaViaje;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;

    @Column(name = "CANTIDADPASAJEROS", nullable = false)
    private Long cantidadPasajeros;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstadoViajeEntities> estadosViaje = new ArrayList<>();

}
