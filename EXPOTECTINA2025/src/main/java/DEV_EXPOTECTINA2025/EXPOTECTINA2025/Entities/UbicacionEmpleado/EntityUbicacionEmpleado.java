package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UbicacionEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter @Setter
@Table(name = "UBICACIONEMPLEADO")
@ToString @EqualsAndHashCode
public class EntityUbicacionEmpleado {

    @Id         //seq_Ubicacion
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Ubicacion")
    @SequenceGenerator(name = "seq_Ubicacion", sequenceName = "seq_Ubicacion", allocationSize = 1)
    private  Long idBitacora;

    @ManyToOne
    @JoinColumn(name = "DUIEMPLEADO", referencedColumnName = "DUIEMPLEADO")
    private EmpleadoEntities duiEmpleado;

    @Column(name = "UBICACION")
    private String ubicacion;

    @Column(name = "FECHAHORAREGISTROE")
    private String fecharegistro;
}
