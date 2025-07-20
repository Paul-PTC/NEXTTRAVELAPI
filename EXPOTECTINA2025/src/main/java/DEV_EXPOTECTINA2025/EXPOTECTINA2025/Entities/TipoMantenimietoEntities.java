package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CollectionId;

@Entity
@Table(name = "TIPOMANTENIMIENTO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TipoMantenimietoEntities {
    @Id
    @Column( name = "idTipoMantenimiento")
    private Long idTipoMantenimiento;

    @Column(name= "nombreTipo")
    private String nombreTipo;

    @Column(name = "descripcion")
    private String descripcion;
}
