package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.TiempoEstimadoLleagada;


import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TiempoLlegadaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.TiempoEstiamdoLlegada.TiempoLlegadaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/apiusuarios/tiempoEstimado")
@RequiredArgsConstructor
public class TiemporEstimadoLLegadaController {

    private final TiempoLlegadaService services;

    @GetMapping("/ObtenerTiempo")
    public ResponseEntity<List<TiempoLlegadaDTO>> obtenerTiempoLlegada() {
        log.info("GET /ObtenerTiempo");
        return ResponseEntity.ok(services.getAllTiempos());
    }

    @GetMapping("/{idTiempo}")
    public ResponseEntity<?> getById(@PathVariable Long idTiempo) {
        log.info("GET /{}", idTiempo);
        TiempoLlegadaDTO dto = services.getById(idTiempo);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not Found",
                    "message", "Tiempo estimado no encontrado",
                    "timestamp", Instant.now().toString()
            ));
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/InsertarTiempos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registrarTiempoLlegada(@Valid @RequestBody TiempoLlegadaDTO tiempo,
                                                    BindingResult br,
                                                    HttpServletRequest request) {
        log.info("POST /InsertarTiempos payload={}", tiempo);
        if (br.hasErrors()) return ResponseEntity.badRequest().body(mapearErrores(br));

        try {
            TiempoLlegadaDTO res = services.insertarTiempoLlegada(tiempo);
            if (res == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Insercion Incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos de tiempo inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", res
            ));
        } catch (Exception e) {
            log.error("Error al registrar tiempo estimado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar tiempo estimado",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping(value = "/ActualizarTiempo/{idtiempo}", consumes = "application/json")
    public ResponseEntity<?> actualizarTiemposLlegada(@PathVariable Long idtiempo,
                                                      @Valid @RequestBody TiempoLlegadaDTO dto,
                                                      BindingResult br) {
        log.info("PUT /ActualizarTiempo/{} payload={}", idtiempo, dto);
        if (br.hasErrors()) return ResponseEntity.badRequest().body(mapearErrores(br));

        try {
            TiempoLlegadaDTO actualizado = services.actualizarTiempoLlegada(idtiempo, dto);
            if (actualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "error", "Not Found",
                        "message", "Tiempo estimado no encontrado",
                        "timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok(actualizado);
        } catch (IllegalStateException e) { // por ejemplo, duplicados lógicos
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Conflict",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            log.error("Error al actualizar tiempo estimado {}", idtiempo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al actualizar tiempo estimado",
                    "detail", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/EliminarTiempoLlegada/{idtiempo}")
    public ResponseEntity<?> eliminarTiempoLlegada(@PathVariable Long idtiempo) {
        log.info("DELETE /EliminarTiempoLlegada/{}", idtiempo);
        try {
            boolean eliminado = services.eliminarTiempoLlegada(idtiempo);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Error-Message", "Tiempo estimado no encontrado")
                        .body(Map.of(
                                "error", "Not Found",
                                "message", "El tiempo estimado no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Tiempo estimado eliminado exitosamente"
            ));
        } catch (Exception e) {
            log.error("Error al eliminar tiempo estimado {}", idtiempo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al eliminar tiempo estimado",
                    "detail", e.getMessage()
            ));
        }
    }

    // Util: mapeo de errores de validación
    private Map<String, Object> mapearErrores(BindingResult br) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError fe : br.getFieldErrors()) {
            errores.put(fe.getField(), fe.getDefaultMessage());
        }
        return Map.of("status", "validation_error", "errors", errores);
    }
}