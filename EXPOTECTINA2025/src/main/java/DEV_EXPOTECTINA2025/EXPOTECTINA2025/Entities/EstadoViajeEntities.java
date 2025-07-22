package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Entity
@Table(name = "ESTADOVIAJE")
public class EstadoViajeEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IDRESERVA", nullable = false)
    private ReservaEntities reserva;

    @NotBlank
    @Column(nullable = false, length = 45)
    private String estado;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaEstado;

    @Column(length = 45)
    private String observacion;
    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ReservaEntities getReserva() { return reserva; }
    public void setReserva(ReservaEntities reserva) { this.reserva = reserva; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaEstado() { return fechaEstado; }
    public void setFechaEstado(Date fechaEstado) { this.fechaEstado = fechaEstado; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

}
