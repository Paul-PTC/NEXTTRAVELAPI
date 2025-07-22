package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESERVA")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRESERVA")
    private Long idReserva;

    @Column(name = "FECHARESERVA", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "FECHAVIAJE", nullable = false)
    private LocalDate fechaViaje;

    @Column(name = "ESTADO", length = 20)
    private String estado;

    @Column(name = "CANTIDADPASAJEROS", nullable = false)
    private int cantidadPasajeros;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    // Relación ManyToOne con Empleado
    @ManyToOne
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO")
    private EmpleadoEntities empleado;

    // Relación ManyToOne con Ruta
    @ManyToOne
    @JoinColumn(name = "IDRUTA", referencedColumnName = "IDRUTA")
    private RutaEntities ruta;

    // Relación OneToMany con Feedback (opcional, si quieres incluirlo)
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<FeedbackEntity> feedbacks;

    // ✅ Relación OneToMany con CALIFICACION
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<CalificacionEntities> calificaciones = new ArrayList<>();
}

