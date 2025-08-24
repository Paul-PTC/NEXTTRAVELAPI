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
public class MensajeDTO {

    private Long idMensaje;

    @NotNull(message = "El ID del chat es obligatorio")
    private Long idChat; // FK → Chat

    // Remitentes opcionales (uno u otro)
    @Size(max = 10, message = "El DUI del cliente no puede superar 10 caracteres")
    private String duiClienteRemitente;

    @Size(max = 10, message = "El DUI del empleado no puede superar 10 caracteres")
    private String duiEmpleadoRemitente;

    @NotBlank(message = "El contenido del mensaje es obligatorio")
    private String contenido; // CLOB

    private LocalDateTime fechaHora; // DB tiene DEFAULT CURRENT_TIMESTAMP

    @Pattern(regexp = "^[SN]$", message = "El campo 'leido' debe ser 'S' o 'N'")
    private String leido; // 'S' / 'N'
}
