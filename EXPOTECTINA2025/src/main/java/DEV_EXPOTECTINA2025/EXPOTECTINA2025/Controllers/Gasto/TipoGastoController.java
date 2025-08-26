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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/apitipogasto")
@RequiredArgsConstructor
public class TipoGastoController {

    private final TipoGastoService tipoGastoService;

    // listar
    @GetMapping("/tipos")
    public ResponseEntity<List<TipoGastoDTO>> listar() {
        return ResponseEntity.ok(tipoGastoService.listar());
    }

    // obtener por id
    @GetMapping("/tipos/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            var opt = tipoGastoService.obtenerPorId(id);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tipo de gasto no encontrado"));
        } catch (Exception e) {
            log.error("Error al obtener tipo de gasto {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // crear
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody TipoGastoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors())
                errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoGastoService.crear(dto));
        } catch (Exception e) {
            log.error("Error al crear tipo de gasto: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear tipo de gasto", "detalle", e.getMessage()));
        }
    }

    // actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody TipoGastoDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors())
                errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            var opt = tipoGastoService.actualizar(id, dto);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tipo de gasto no encontrado"));
        } catch (Exception e) {
            log.error("Error al actualizar tipo de gasto {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar tipo de gasto", "detalle", e.getMessage()));
        }
    }

    // eliminar
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
