package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Pago;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.PuntosClienteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Pago.PuntosClienteService;
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
@RequestMapping("/apipuntoscliente")
@RequiredArgsConstructor
public class PuntosClienteController {

    private PuntosClienteService puntosService;

    // GET: listar todos
    @GetMapping("/puntos")
    public ResponseEntity<List<PuntosClienteDTO>> obtenerTodos() {
        return ResponseEntity.ok(puntosService.obtenerTodos());
    }

    // GET: obtener por ID
    @GetMapping("/puntos/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(puntosService.obtenerPorId(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "PuntosCliente no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al obtener PuntosCliente {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: crear registro de puntos
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@Valid @RequestBody PuntosClienteDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            PuntosClienteDTO creado = puntosService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al registrar PuntosCliente: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al registrar", "detalle", e.getMessage()));
        }
    }

    // PUT: actualizar registro de puntos
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody PuntosClienteDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            PuntosClienteDTO actualizado = puntosService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "PuntosCliente no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar PuntosCliente {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar registro de puntos
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = puntosService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "PuntosCliente no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "PuntosCliente eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar PuntosCliente {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar", "detalle", e.getMessage()));
        }
    }
}

