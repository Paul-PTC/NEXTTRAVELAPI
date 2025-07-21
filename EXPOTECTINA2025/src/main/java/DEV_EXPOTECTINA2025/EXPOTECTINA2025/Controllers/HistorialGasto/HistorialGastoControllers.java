package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.HistorialGasto;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.HistorialGastoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.LugarTuristicoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.HistorialGasto.HistorialGastoServices;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico.LugarTuristicoServices;
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
@RequestMapping("/apiHistorialGasto")
public class HistorialGastoControllers {
    @Autowired
    private HistorialGastoServices ServicesHistorialServices;

    @GetMapping("/HistorialGasto")
    public List<HistorialGastoDTO> DatosLugar(){
        return ServicesHistorialServices.getAllHistorialGasto();
    }

    @PostMapping("/insertarHistorialGasto")
    public ResponseEntity<Map<String, Object>> registrarHistorialGasto(
            @Valid @RequestBody HistorialGastoDTO historialGastoDTO,
            HttpServletRequest request) {

        try {
            // Intentamos guardar los datos
            HistorialGastoDTO respuesta = ServicesHistorialServices.insertarHistorialGasto(historialGastoDTO);
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del historial inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", respuesta
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar historial",
                            "detail", e.getMessage()
                    ));
        }
    }

    @PutMapping("/actualizarHistorialGasto/{idHistorial}")
    public ResponseEntity<?> modificarHistorialGasto(
            @PathVariable Long idHistorial,
            @Valid @RequestBody HistorialGastoDTO historialDTO,
            BindingResult bindingResult) {

        // Validar errores en el DTO
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            HistorialGastoDTO historialActualizado = ServicesHistorialServices.actualizarHistorialGasto(idHistorial, historialDTO);
            return ResponseEntity.ok(historialActualizado);
        } catch (ExceptionsUsuarioNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }
    }


    @DeleteMapping("/eliminarHistorialGasto/{idHistorialGasto}")
    public ResponseEntity<Map<String, Object>> eliminarHistorialGasto(@PathVariable Long idHistorialGasto) {
        try {
            if (!ServicesHistorialServices.EliminarHistorialGasto(idHistorialGasto)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje de error", "Historial de gasto no encontrado")
                        .body(Map.of(
                                "error", "Not found",
                                "mensaje", "El historial de gasto no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Historial de gasto eliminado exitosamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el historial de gasto",
                    "detail", e.getMessage()
            ));
        }
    }

}
