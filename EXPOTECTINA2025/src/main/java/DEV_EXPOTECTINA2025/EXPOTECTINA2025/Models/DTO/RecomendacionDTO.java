package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class RecomendacionDTO {

    private Long idRecomendacion;

    @NotBlank(message = "El DUI del cliente es obligatorio")
    @Size(max = 10, message = "El DUI no puede superar los 10 caracteres")
    private String duiCliente;

    @NotBlank(message = "El tipo de lugar es obligatorio")
    @Size(max = 50, message = "El tipo de lugar no puede superar los 50 caracteres")
    private String tipoLugar;

    @NotBlank(message = "El lugar sugerido es obligatorio")
    @Size(max = 100, message = "El lugar sugerido no puede superar los 100 caracteres")
    private String lugarSugerido;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;
}
