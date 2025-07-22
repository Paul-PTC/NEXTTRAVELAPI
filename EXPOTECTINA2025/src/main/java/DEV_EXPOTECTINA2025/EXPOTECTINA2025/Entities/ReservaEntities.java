package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "RESERVA")
@Getter
@Setter
public class ReservaEntities {
    @Id
    @Column(name = "idreserva")
    private Integer id;

    @Column(name = "DUIEMPLEADO", nullable = false, length = 10)
    private String duiEmpleado;

    @Column(name = "IDRUTA", nullable = false)
    private Integer idRuta;

    @Column(name = "FECHARESERVA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaReserva;

    @Column(name = "FECHAVIAJE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaViaje;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;

    @Column(name = "CANTIDADPASAJEROS", nullable = false)
    private Integer cantidadPasajeros;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    public @NotNull(message = "El ID de la reserva es obligatorio") Integer getIdReserva() {
        return id;
    }

}
