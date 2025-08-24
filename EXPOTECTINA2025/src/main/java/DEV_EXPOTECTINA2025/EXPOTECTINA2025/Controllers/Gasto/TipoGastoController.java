package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Gasto;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TipoGastoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Gasto.TipoGastoService;
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
@RequestMapping("/apitipogasto")
@RequiredArgsConstructor
public class TipoGastoController {

    private TipoGastoService tipoGastoService;

    // GET: obtener todos los tipos de gasto
    @GetMapping("/tiposGasto")
    public ResponseEntity<List<TipoGastoDTO>> obtenerTodos() {
        List<TipoGastoDTO> tipos = tipoGastoService.obtenerTodos();
        return ResponseEntity.ok(tipos);
    }

    // GET: obtener tipo de gasto por ID
    @GetMapping("/tiposGasto/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            TipoGastoDTO dto = tipoGastoService.obtenerPorId(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tipo de gasto no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al obtener tipo de gasto {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: crear tipo de gasto
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@Valid @RequestBody TipoGastoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            TipoGastoDTO creado = tipoGastoService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al registrar tipo de gasto: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al registrar tipo de gasto", "detalle", e.getMessage()));
        }
    }

    // PUT: actualizar tipo de gasto
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody TipoGastoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            TipoGastoDTO actualizado = tipoGastoService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tipo de gasto no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar tipo de gasto {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar tipo de gasto", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar tipo de gasto
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = tipoGastoService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Tipo de gasto no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Tipo de gasto eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar tipo de gasto {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar tipo de gasto", "detalle", e.getMessage()));
        }
    }
}

