package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.CodigoVerificacionDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat.CodigoVerificacionService;
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
@RequestMapping("/apicodigoverificacion")
@RequiredArgsConstructor
public class CodigoVerificacionController {

    private CodigoVerificacionService codigoService;

    // GET: listar todos
    @GetMapping("/codigos")
    public ResponseEntity<List<CodigoVerificacionDTO>> listar() {
        return ResponseEntity.ok(codigoService.obtenerTodos());
    }

    // GET: obtener por ID
    @GetMapping("/codigos/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(codigoService.obtenerPorId(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Código no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al obtener código {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: crear código
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody CodigoVerificacionDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            CodigoVerificacionDTO creado = codigoService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al crear código: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear código", "detalle", e.getMessage()));
        }
    }

    // PUT: actualizar código
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody CodigoVerificacionDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            CodigoVerificacionDTO actualizado = codigoService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Código no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar código {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar código", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar código
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = codigoService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Código no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Código eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar código {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar código", "detalle", e.getMessage()));
        }
    }
}

