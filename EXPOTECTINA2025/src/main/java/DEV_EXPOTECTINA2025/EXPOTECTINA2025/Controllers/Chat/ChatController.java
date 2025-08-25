package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ChatDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat.ChatService;
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
@RequestMapping("/apichat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // GET: listar todos los chats
    @GetMapping("/chats")
    public ResponseEntity<List<ChatDTO>> obtenerTodos() {
        List<ChatDTO> chats = chatService.obtenerTodos();
        return ResponseEntity.ok(chats);
    }

    // GET: obtener chat por ID
    @GetMapping("/chats/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            ChatDTO chat = chatService.obtenerPorId(id);
            return ResponseEntity.ok(chat);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Chat no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al obtener chat {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: insertar chat (estilo Map como TipoMantenimiento)
    @PostMapping("/insertar")
    public ResponseEntity<Map<String, Object>> registrar(
            @Valid @RequestBody ChatDTO dto,
            BindingResult result) {

        // Validación de campos (Bean Validation)
        if (result.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            result.getFieldErrors().forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "Insercion Incorrecta",
                    "errorType", "VALIDATION_ERROR",
                    "message", "Datos para insercion invalidos",
                    "errors", fieldErrors
            ));
        }

        try {
            ChatDTO res = chatService.registrar(dto);  // crea y devuelve el DTO persistido
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", res
            ));
        }
        // Cuando no existen las FKs (Cliente/Empleado)
        catch (jakarta.persistence.EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "errorType", "NOT_FOUND",
                    "message", ex.getMessage()
            ));
        }
        // Cuando vienen DUIs vacíos o nulos (guard clausule del service)
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "Insercion Incorrecta",
                    "errorType", "VALIDATION_ERROR",
                    "message", ex.getMessage()
            ));
        }
        // Cualquier otro error inesperado
        catch (Exception e) {
            log.error("Error al registrar chat", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar el chat",
                    "detail", e.getMessage()
            ));
        }
    }


    // PUT: actualizar chat
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ChatDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            ChatDTO actualizado = chatService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Chat no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar chat {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar chat", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar chat
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = chatService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Chat no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Chat eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar chat {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar chat", "detalle", e.getMessage()));
        }
    }
}
