package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RutasGuardadasDTO {

    private Long idRutasGuardadas;

    @NotBlank(message = "El DUI del cliente es obligatorio")
    private String duiCliente;

    @NotNull(message = "El ID de la ruta es obligatorio")
    private Long idRuta;

    @NotBlank(message = "El nombre de la ruta es obligatorio")
    @Size(max = 100, message = "El nombre de la ruta no puede tener más de 100 caracteres")
    private String nombreRuta;

    @NotNull(message = "La fecha de guardado es obligatoria")
    private LocalDateTime fechaGuardado;
}
