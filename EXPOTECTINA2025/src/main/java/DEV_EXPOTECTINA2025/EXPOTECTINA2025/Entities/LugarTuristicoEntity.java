package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "LUGARTURISTICO")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LugarTuristicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lugar_turistico")
    @SequenceGenerator(name = "seq_lugar_turistico", sequenceName = "seq_lugar_turistico", allocationSize = 1)
    @Column(name = "IDLUGAR")
    private Long idLugar;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "TIPOLUGAR", length = 50)
    private String tipoLugar;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "LATITUD", nullable = false, precision = 10, scale = 6)
    private BigDecimal latitud;

    @Column(name = "LONGITUD", nullable = false, precision = 10, scale = 6)
    private BigDecimal longitud;
}
