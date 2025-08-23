package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class DescuentoAplicadoDTO {

    private Long idDescuento;
    private Long idReserva;
    private Long idPromocion;

    @NotNull(message = "EL Monto Descontado es obligatorio")
    private String montoDescontado;
    @NotNull(message = "la Fecha Aplicacion es obligatorio")
    private Date fechaAplicacion;
}
