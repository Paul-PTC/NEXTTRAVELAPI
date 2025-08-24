package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "MENSAJE")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MensajeEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mensaje")
    @SequenceGenerator(name = "seq_mensaje", sequenceName = "SEQ_MENSAJE", allocationSize = 1)
    @Column(name = "IDMENSAJE")
    private Long idMensaje;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDCHAT", referencedColumnName = "IDCHAT", nullable = false)
    private ChatEntities chat;

    // Remitentes opcionales; uno u otro puede venir null según quién envía
    @ManyToOne
    @JoinColumn(name = "DUICLIENTEREMITENTE", referencedColumnName = "DUICLIENTE")
    private ClienteEntities clienteRemitente;

    @ManyToOne
    @JoinColumn(name = "DUIEMPLEADOREMITENTE", referencedColumnName = "DUIEMPLEADO")
    private EmpleadoEntities empleadoRemitente;

    @Lob
    @Column(name = "CONTENIDO", nullable = false)
    private String contenido; // CLOB

    @Column(name = "FECHAHORA")
    private LocalDateTime fechaHora; // DEFAULT CURRENT_TIMESTAMP en DB

    @Column(name = "LEIDO", length = 1)
    private String leido; // 'S' o 'N'
}
