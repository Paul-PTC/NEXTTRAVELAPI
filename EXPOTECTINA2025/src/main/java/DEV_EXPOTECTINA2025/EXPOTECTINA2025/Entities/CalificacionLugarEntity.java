package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

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
    @SequenceGenerator(name = "seq_CalificaionLugar", sequenceName = "seq_CalificaionLugar", allocationSize = 1)
    private Long idCalificacionLugar;

    // Relación con LugarTuristicoEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDLUAGAR", nullable = false)
    private LugarTuristicoEntity lugar;

    // Relación con UsuarioEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private UserEntity usuario;

    @Column(nullable = false)
    private int calificacion; // CHECK entre 1 y 5 se hace en la base de datos

    @Column(length = 250)
    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fechaCalificacion;
}

