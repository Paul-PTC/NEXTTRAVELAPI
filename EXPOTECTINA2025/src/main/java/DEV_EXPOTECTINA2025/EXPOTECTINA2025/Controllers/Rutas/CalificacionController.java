package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.CalificacionDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.UserDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas.CalificacionServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/apiCalificacion")
public class CalificacionController {

    @Autowired
    private CalificacionServices servicio;

    @GetMapping("/obtenerCalificaciones")
    public List<CalificacionDTO> obtenerCalificaciones() {
        return servicio.getAllCalificaciones();
    }

    @PostMapping("/ingresarCalificacion")
    public ResponseEntity<Map<String, Object>> registrarCalificacion(@Valid @RequestBody CalificacionDTO calificacion, HttpServletRequest request) {
        try {
            // Intento de guardar calificación
            CalificacionDTO respuesta = servicio.insertarCalificaciones(calificacion);

            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos de la calificación inválidos o reserva no encontrada"
                ));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", respuesta
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar calificación",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/modificarCalificacion/{id}")
    public ResponseEntity<?> modificarCalificacion(
            @PathVariable Long id,
            @Valid @RequestBody CalificacionDTO calificacionDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            CalificacionDTO actualizada = servicio.actualizarCalificacion(id, calificacionDTO);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "data", actualizada
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "status", "error",
                            "message", "No se encontró la calificación con ID: " + id
                    ));
        } catch (Exception e) {
            log.error("Error al actualizar calificación con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(
                            "status", "error",
                            "message", "Error al actualizar calificación",
                            "detail", e.getMessage()
                    ));
        }
    }
    @DeleteMapping("/eliminarCalificacion/{id}")
    public ResponseEntity<Map<String, Object>> eliminarCalificacion(@PathVariable Long id) {
        try {
            // Intenta eliminar la calificación desde el servicio
            boolean eliminado = servicio.eliminarCalificacion(id);

            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje-Error", "Calificación no encontrada")
                        .body(Map.of(
                                "error", "Not found",
                                "mensaje", "La calificación no ha sido encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }

            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Calificación eliminada exitosamente"
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar la calificación",
                    "detail", e.getMessage()
            ));
        }
    }

}
