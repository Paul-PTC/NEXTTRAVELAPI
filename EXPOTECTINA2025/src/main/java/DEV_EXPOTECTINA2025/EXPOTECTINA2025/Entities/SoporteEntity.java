package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "SOPORTE")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SoporteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_soporte")
    @SequenceGenerator(name = "seq_soporte", sequenceName = "seq_Soporte", allocationSize = 1)
    @Column(name = "IDSOPORTE")
    private Long idSoporte;

    @Column(name = "DUICLIENTE", nullable = false, length = 10)
    private String duiCliente;

    @Column(name = "DUIEMPLEADO", nullable = false, length = 10)
    private String duiEmpleado;

    @Column(name = "ASUNTO", nullable = false, length = 100)
    private String asunto;

    @Column(name = "MENSAJE", nullable = false, length = 300)
    private String mensaje;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;

    @Column(name = "FECHASOLICITUD", nullable = false)
    private LocalDateTime fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "DUICLIENTE", referencedColumnName = "DUICLIENTE", insertable = false, updatable = false)
    private ClienteEntities cliente;

    @ManyToOne
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO", insertable = false, updatable = false)
    private EmpleadoEntities empleado;
}

