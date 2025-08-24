package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICACION")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NotificacionEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notificacion")
    @SequenceGenerator(name = "seq_notificacion", sequenceName = "SEQ_NOTIFICACION", allocationSize = 1)
    @Column(name = "IDNOTIFICACION")
    private Long idNotificacion;

    @ManyToOne(optional = true)
    @JoinColumn(name = "DUICLIENTE", referencedColumnName = "DUICLIENTE")
    private ClienteEntities cliente; // FK → Cliente(duiCliente)

    @ManyToOne(optional = true)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO")
    private EmpleadoEntities empleado; // FK → Empleado(duiEmpleado)

    @ManyToOne(optional = true)
    @JoinColumn(name = "IDMENSAJE", referencedColumnName = "IDMENSAJE")
    private MensajeEntities mensajeRelacionado; // FK → Mensaje(idMensaje)

    @Column(name = "MENSAJE", length = 300)
    private String mensaje;

    @Column(name = "LEIDO", length = 1, nullable = false)
    private String leido = "N"; // Por defecto 'N'

    @Column(name = "FECHAHORA", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Column(name = "TIPO", length = 50)
    private String tipo;
}
