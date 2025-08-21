package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@EqualsAndHashCode
@Getter @Setter
@ToString
public class DTOPago {
    @NotNull(message = "El ID de Pago es obligatorio")
    private Long idPago;
    @NotNull(message = "El ID de Reserva es obligatorio")
    private Long idReserva;

    @Min(value = 0, message = "El monto debe ser un número positivo")
    private float Monto;

    @NotNull(message = "La fecha del pago es obligatoria")
    private Date fechaPago;

    @Size(min = 4, message = "El Estado no puede contronener menos de 4 Caracteres")
    private String estado;
}
