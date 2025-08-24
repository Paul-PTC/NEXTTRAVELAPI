package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDTO {

    private Long idChat;

    @NotBlank(message = "El DUI del cliente es obligatorio")
    @Size(max = 10, message = "El DUI del cliente no puede superar 10 caracteres")
    private String duiCliente;

    @NotBlank(message = "El DUI del empleado es obligatorio")
    @Size(max = 10, message = "El DUI del empleado no puede superar 10 caracteres")
    private String duiEmpleado;

    private LocalDateTime fechaInicio;   // puede venir null; DB tiene DEFAULT CURRENT_TIMESTAMP

    @Size(max = 20, message = "El estado no puede superar 20 caracteres")
    private String estado;

    private LocalDateTime ultimoMensaje;
}
