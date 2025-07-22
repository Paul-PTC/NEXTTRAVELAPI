package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class SoporteDTO {

    private Long idSoporte;

    @NotBlank(message = "El DUI del cliente es obligatorio")
    private String duiCliente;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    private String duiEmpleado;

    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotBlank(message = "El tipo de soporte es obligatorio")
    private String tipo;

    @NotBlank(message = "El estado del soporte es obligatorio")
    private String estado;

    @NotNull(message = "La fecha de solicitud es obligatoria")
    private LocalDateTime fechaSolicitud;
}
