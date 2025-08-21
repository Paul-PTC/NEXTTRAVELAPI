package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "UBICACIONEMPLEADO")
@ToString
@EqualsAndHashCode @Getter @Setter
public class EntitesUbicacionEmpleado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UBICACIONEMPLEADO")
    @SequenceGenerator(name = "SEQ_UBICACIONEMPLEADO", sequenceName = "SEQ_UBICACIONEMPLEADO", allocationSize = 1)
    @Column(name = "IDUBICACIONEMPLEADO")
    private Long idUbicacionEmpleado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO")
    private EmpleadoEntities duiEmpleado;

    @Size(min = 1)
    @Column(name = "UBICACION")
    private String ubicacionEmpleado;


    @Column(name = "FECHAHORAREGISTRO", nullable = false)
    private LocalDateTime fechaRegistro;


}
