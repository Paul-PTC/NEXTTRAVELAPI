package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Usuario;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.UserDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.UsuarioServices.UsuarioServices;
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
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/apiusuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServices usuarioService;

    // GET: listar todos
    @GetMapping("/usuarios")
    public ResponseEntity<List<UserDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // GET: obtener por ID
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            UserDTO dto = usuarioService.obtenerPorId(id);
            return ResponseEntity.ok(dto);
        } catch (NoSuchElementException | jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "Usuario no encontrado", "detail", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al obtener usuario {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Error interno", "detail", e.getMessage()));
        }
    }

    // POST: crear usuario
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody UserDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            UserDTO creado = usuarioService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) { // por ejemplo: usuario/correo duplicado, validación de negocio
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al crear usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Error al crear usuario", "detail", e.getMessage()));
        }
    }

    // PUT: actualizar usuario
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody UserDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            UserDTO actualizado = usuarioService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException | jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "Usuario no encontrado", "detail", e.getMessage()));
        } catch (IllegalArgumentException e) { // colisiones de unique u otras reglas
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar usuario {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Error al actualizar usuario", "detail", e.getMessage()));
        }
    }

    // DELETE: eliminar usuario
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = usuarioService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("status", "error", "message", "Usuario no encontrado"));
            }
            return ResponseEntity.ok(Map.of("status", "success", "message", "Usuario eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar usuario {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", "Error al eliminar usuario", "detail", e.getMessage()));
        }
    }
}

