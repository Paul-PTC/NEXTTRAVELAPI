package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.MensajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat.MensajeService;
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

@Slf4j
@RestController
@RequestMapping("/apimensaje")
@RequiredArgsConstructor
public class MensajeController {

    private MensajeService mensajeService;

    // GET: listar todos los mensajes
    @GetMapping("/mensajes")
    public ResponseEntity<List<MensajeDTO>> obtenerTodos() {
        List<MensajeDTO> mensajes = mensajeService.obtenerTodos();
        return ResponseEntity.ok(mensajes);
    }

    // GET: obtener mensaje por ID
    @GetMapping("/mensajes/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            MensajeDTO mensaje = mensajeService.obtenerPorId(id);
            return ResponseEntity.ok(mensaje);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Mensaje no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al obtener mensaje {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: crear mensaje
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@Valid @RequestBody MensajeDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            MensajeDTO creado = mensajeService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al registrar mensaje: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al registrar mensaje", "detalle", e.getMessage()));
        }
    }

    // PUT: actualizar mensaje
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody MensajeDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            MensajeDTO actualizado = mensajeService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Mensaje no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar mensaje {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar mensaje", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar mensaje
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = mensajeService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Mensaje no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Mensaje eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar mensaje {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar mensaje", "detalle", e.getMessage()));
        }
    }
}
