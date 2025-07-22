package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.NotBlank;
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
public class EstadoViajeDTO {
    private Integer id;

    @NotNull
    private Integer idReserva;

    @NotBlank
    private String estado;

    @NotNull
    private Date fechaEstado;

    private String observacion;

}
