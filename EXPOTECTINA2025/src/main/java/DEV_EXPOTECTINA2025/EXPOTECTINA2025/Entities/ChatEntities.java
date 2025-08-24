package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "CHAT")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ChatEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chat")
    @SequenceGenerator(name = "seq_chat", sequenceName = "SEQ_CHAT", allocationSize = 1)
    @Column(name = "IDCHAT")
    private Long idChat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUICLIENTE", referencedColumnName = "DUICLIENTE", nullable = false)
    private ClienteEntities cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO", nullable = false)
    private EmpleadoEntities empleado;

    @Column(name = "FECHAINICIO")
    private LocalDateTime fechaInicio;   // DEFAULT CURRENT_TIMESTAMP en DB

    @Column(name = "ESTADO", length = 20)
    private String estado;

    @Column(name = "ULTIMOMENSAJE")
    private LocalDateTime ultimoMensaje;
}
