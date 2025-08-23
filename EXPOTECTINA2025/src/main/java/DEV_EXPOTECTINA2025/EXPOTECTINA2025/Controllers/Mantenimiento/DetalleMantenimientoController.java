package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Mantenimiento;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionDetalleMantenimientoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DetalleMantenimientoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Mantenimiento.DetalleMantenimientoServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiDetalleMantenimiento")
public class DetalleMantenimientoController {
    @Autowired
    private DetalleMantenimientoServices services;

    @GetMapping("/DetallesMantenimiento")
    private List<DetalleMantenimientoDTO> obtenerDetalles() {
        return services.getAllDetallesMantenimiento();
    }

    @PostMapping("/insertarDetalleMantenimiento")
    public ResponseEntity<Map<String, Object>> registrarDetalleMantenimiento(@Valid @RequestBody DetalleMantenimientoDTO detalleMantenimiento, HttpServletRequest request) {
        try {
            //Intentamos guardar los datos
            DetalleMantenimientoDTO respuesta = services.insertarDetalleMantenimiento(detalleMantenimiento);
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del detalle inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", respuesta
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar usuario",
                            "detail", e.getMessage()
                    ));
        }
    }

    @PutMapping("/ActualizarDetalleMantenimiento/{idDetalleMantenimiento}")
    public ResponseEntity<?> modificarDetalleMantenimiento(
            @PathVariable Long idDetalleMantenimiento ,
            @Valid @RequestBody DetalleMantenimientoDTO detalleDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            DetalleMantenimientoDTO detalleActualizado = services.actualizarDetalle(idDetalleMantenimiento, detalleDTO);
            return ResponseEntity.ok(detalleActualizado);
        } catch (ExceptionDetalleMantenimientoNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }

    }
    @DeleteMapping("EliminarDetalleMantenimiento/{idDetalleMantenimiento}")
    public ResponseEntity<Map<String, Object>> eliminarRangoEmp(@PathVariable Long idDetalleMantenimiento){
        try {
            if (!services.EliminarDetalleMantenimiento(idDetalleMantenimiento)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje de error", "Detalle no encontrado")
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "El rango no ha sido encontrado",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Detalle eliminadoo exitosamente"  // Mensaje de éxito
            ));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el usuario",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }

    }
}
