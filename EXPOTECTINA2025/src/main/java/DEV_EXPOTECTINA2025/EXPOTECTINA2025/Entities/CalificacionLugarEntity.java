package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CALIFICACIONLUGAR")  // ← Nombre exacto de la tabla en mayúsculas
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionLugarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_CalificaionLugar")
    @Column(name = "IDCALIFICACIONLUGAR")
    @SequenceGenerator(name = "seq_CalificaionLugar", sequenceName = "SEQ_CALIFICACIONLUGAR", allocationSize = 1)
    private Long idCalificacionLugar;

    // Relación con LugarTuristicoEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDLUGAR", nullable = false)
    private LugarTuristicoEntity lugar;

    // Relación con UsuarioEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private UserEntity usuario;

    @Column(name = "CALIFICACION", nullable = false)
    private int calificacion; // CHECK entre 1 y 5 se hace en la base de datos

    @Column(name = "COMENTARIO", length = 250)
    private String comentario;

    @Column(name = "FECHACALIFICACION", nullable = false)
    private LocalDateTime fechaCalificacion;
}

