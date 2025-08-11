package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "RUTA")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RutaEntities {
    @Id
    @Column(name = "IDRUTA")
    private Long idRuta;

    @ManyToOne
    @JoinColumn(name = "IDLUGAR", referencedColumnName = "IDLUGAR", nullable = false)
    private LugarTuristicoEntity idLugar;

    @Column(name = "ORIGEN")
    private String origen;

    @Column(name = "DESTINO")
    private String destino;

     @Column(name = "DISTANCIAKM")
    private BigDecimal distanciaKM;

     @Column(name = "DURACIONESTIMADA")
    private Date duracionEstimada;

     @Column(name = "DESCRIPCION")
    private String descripcion;

     @Column(name = "ESTADO")
    private String estado;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL)
    private List<ReservaEntities> reservas = new ArrayList<>();

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RutasGuardadasEntity> rutasGuardadas = new ArrayList<>();

}
