package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificacionDTO {

    private Long idNotificacion;

    @Size(max = 10, message = "El DUI del cliente no puede superar los 10 caracteres")
    private String duiCliente;

    @Size(max = 10, message = "El DUI del empleado no puede superar los 10 caracteres")
    private String duiEmpleado;

    private Long idMensaje;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(max = 300, message = "El mensaje no puede superar los 300 caracteres")
    private String mensaje;

    @Pattern(regexp = "^[SN]$", message = "El campo 'leído' solo puede ser 'S' o 'N'")
    private String leido;

    @NotNull(message = "La fecha y hora de la notificación es obligatoria")
    private LocalDateTime fechaHora;

    @Size(max = 50, message = "El tipo no puede superar los 50 caracteres")
    private String tipo;
}
