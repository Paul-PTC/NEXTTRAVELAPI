package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Mantenimiento;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.MantenimientoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Mantenimiento.MantenimientoServices;
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
@RequestMapping("/apimantenimiento")
@RequiredArgsConstructor
public class MantenimientoController {

    private final MantenimientoServices mantenimientoService;

    // listar
    @GetMapping("/mantenimientos")
    public ResponseEntity<List<MantenimientoDTO>> listar() {
        return ResponseEntity.ok(mantenimientoService.listar());
    }

    // obtener por id
    @GetMapping("/mantenimientos/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            var opt = mantenimientoService.obtenerPorId(id);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Mantenimiento no encontrado"));
        } catch (Exception e) {
            log.error("Error al obtener mantenimiento {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // crear
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody MantenimientoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(mantenimientoService.crear(dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al crear mantenimiento: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear mantenimiento", "detalle", e.getMessage()));
        }
    }

    // actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody MantenimientoDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            var opt = mantenimientoService.actualizar(id, dto);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Mantenimiento no encontrado"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar mantenimiento {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar mantenimiento", "detalle", e.getMessage()));
        }
    }

    // eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = mantenimientoService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Mantenimiento no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Mantenimiento eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar mantenimiento {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar mantenimiento", "detalle", e.getMessage()));
        }
    }
}

