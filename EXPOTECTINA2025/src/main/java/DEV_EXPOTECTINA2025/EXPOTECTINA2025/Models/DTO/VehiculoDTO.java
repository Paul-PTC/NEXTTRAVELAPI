package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class VehiculoDTO {
    @Positive(message = "El ID del vehículo debe ser un número positivo")
    private Integer idVehiculo;

    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 10, message = "La placa no debe exceder los 10 caracteres")
    private String placa;

    @Size(max = 50, message = "La marca no debe exceder los 50 caracteres")
    private String marca;

    @Size(max = 20, message = "El modelo no debe exceder los 20 caracteres")
    private String modelo;

    @Positive(message = "El año debe ser un número positivo")
    private Integer anio;

    @FutureOrPresent(message = "La fecha de vencimiento de la circulación debe ser hoy o una fecha futura")
    private Date fechaVencimientoCirculacion;

    @FutureOrPresent(message = "La fecha de vencimiento del seguro debe ser hoy o una fecha futura")
    private Date fechaVencimientoSeguro;

    @FutureOrPresent(message = "La fecha de vencimiento de la revisión debe ser hoy o una fecha futura")
    private Date fechaVencimientoRevision;


}


