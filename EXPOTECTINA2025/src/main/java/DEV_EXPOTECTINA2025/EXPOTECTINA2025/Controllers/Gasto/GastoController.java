package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Gasto;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.GastoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Gasto.GastoService;
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
@RequestMapping("/apigasto")
@RequiredArgsConstructor
public class GastoController {

    private final GastoService gastoService;

    // GET: obtener todos los gastos
    @GetMapping("/gastos")
    public ResponseEntity<List<GastoDTO>> obtenerTodos() {
        List<GastoDTO> gastos = gastoService.obtenerTodos();
        return ResponseEntity.ok(gastos);
    }

    // GET: obtener gasto por ID
    @GetMapping("/gastos/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            GastoDTO dto = gastoService.obtenerPorId(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Gasto no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al obtener gasto {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: registrar gasto
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@Valid @RequestBody GastoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            GastoDTO creado = gastoService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al registrar gasto: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al registrar gasto", "detalle", e.getMessage()));
        }
    }

    // PUT: actualizar gasto
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody GastoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            GastoDTO actualizado = gastoService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Gasto no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar gasto {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar gasto", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar gasto
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = gastoService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Gasto no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Gasto eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar gasto {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar gasto", "detalle", e.getMessage()));
        }
    }
}

