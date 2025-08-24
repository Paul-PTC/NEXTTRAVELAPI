package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.NotificacionDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat.NotificacionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/apinotificacion")
@RequiredArgsConstructor
public class NotificacionController {

    private NotificacionService notificacionService;

    // GET: listar todas las notificaciones
    @GetMapping("/notificaciones")
    public ResponseEntity<List<NotificacionDTO>> obtenerTodos() {
        List<NotificacionDTO> notifs = notificacionService.listar();
        return ResponseEntity.ok(notifs);
    }

    @GetMapping("/notificaciones/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<NotificacionDTO> opt = notificacionService.obtenerPorId(id);
            if (opt.isPresent()) {
                return ResponseEntity.ok(opt.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Notificación no encontrada"));
        } catch (Exception e) {
            log.error("Error al obtener notificación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: crear notificación
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@Valid @RequestBody NotificacionDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            NotificacionDTO creada = notificacionService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (Exception e) {
            log.error("Error al registrar notificación: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al registrar notificación", "detalle", e.getMessage()));
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody NotificacionDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            Optional<NotificacionDTO> actualizado = notificacionService.actualizar(id, dto);
            if (actualizado.isPresent()) {
                return ResponseEntity.ok(actualizado.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Notificación no encontrada"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Notificación no encontrada", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar notificación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar notificación", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar notificación
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = notificacionService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Notificación no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Notificación eliminada correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar notificación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar notificación", "detalle", e.getMessage()));
        }
    }

    @PatchMapping("/leer/{id}")
    public ResponseEntity<?> marcarComoLeida(@PathVariable Long id) {
        try {
            var opt = notificacionService.marcarComoLeida(id); // Optional<NotificacionDTO>
            if (opt.isPresent()) {
                return ResponseEntity.ok(opt.get());           // ResponseEntity<NotificacionDTO>
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // ResponseEntity<Map<String,String>>
                    .body(Map.of("error", "Notificación no encontrada"));
        } catch (Exception e) {
            log.error("Error al marcar como leída la notificación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al marcar como leída", "detalle", e.getMessage()));
        }
    }
}
