package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "ESTADO", uniqueConstraints = {
        @UniqueConstraint(name = "uq_estado_nombre", columnNames = "NOMBREESTADO")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_seq_gen")
    @SequenceGenerator(name = "estado_seq_gen", sequenceName = "SEQ_ESTADO", allocationSize = 1)
    @Column(name = "IDESTADO")
    private Long idEstado;

    @NotBlank(message = "El nombre del estado es obligatorio")
    @Size(max = 50, message = "El nombre del estado no puede tener más de 50 caracteres")
    @Column(name = "NOMBREESTADO", nullable = false, length = 50)
    private String nombreEstado;
}
