package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CALIFICACIONLUGAR")
@Getter @Setter @ToString @EqualsAndHashCode
public class CalificacionLugarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_calificacion_lugar")
    @SequenceGenerator(name = "seq_calificacion_lugar", sequenceName = "seq_CalificaionLugar", allocationSize = 1)
    @Column(name = "IDCALIFICACIONLUGAR")
    private Long idCalificacionLugar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDLUGAR", referencedColumnName = "IDLUGAR", nullable = false)
    private LugarTuristicoEntity lugar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO", nullable = false)
    private UserEntity usuario;

    @Column(name = "CALIFICACION")
    private Integer calificacion; // 1..5

    @Column(name = "COMENTARIO", length = 250)
    private String comentario;

    @Column(name = "FECHACALIFICACION", nullable = false)
    private LocalDateTime fechaCalificacion;
}

