package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@GastoEntity
@Table(name = "RESERVA")
@Getter @Setter
public class ReservaEntities {
    @Id
    @Column(name = "IDRESERVA")

    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO", nullable = false)
    private EmpleadoEntities empleado;   // ← AQUÍ está la relación correcta

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDRUTA", referencedColumnName = "IDRUTA", nullable = false)
    private RutaEntities ruta; // o deja Long idRuta si prefieres scalar

    @Column(name = "FECHARESERVA", nullable = false)
    private LocalDateTime fechaReserva;

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

    @OneToMany(mappedBy = "idreserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntitesPago> reserva = new ArrayList<>();
}

