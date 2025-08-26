package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.RutasController;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RutaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas.RutaService;
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
@RequestMapping("/apiruta")
@RequiredArgsConstructor
public class RutasController {

    private final RutaService rutaService;

    // listar
    @GetMapping("/rutas")
    public ResponseEntity<List<RutaDTO>> listar() {
        return ResponseEntity.ok(rutaService.listar());
    }

    // obtener por id
    @GetMapping("/rutas/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            var opt = rutaService.obtenerPorId(id);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ruta no encontrada"));
        } catch (Exception e) {
            log.error("Error al obtener ruta {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // crear
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody RutaDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(rutaService.crear(dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al crear ruta: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear ruta", "detalle", e.getMessage()));
        }
    }

    // actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody RutaDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            var opt = rutaService.actualizar(id, dto);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ruta no encontrada"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar ruta {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar ruta", "detalle", e.getMessage()));
        }
    }

    // eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = rutaService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Ruta no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Ruta eliminada correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar ruta {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar ruta", "detalle", e.getMessage()));
        }
    }
}
