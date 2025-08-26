package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.CalificacionLugarDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico.CalificacionLugarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/apicalificacion")
@RequiredArgsConstructor
public class CalificacionLugarController {

    private final CalificacionLugarService calificacionService;

    // listar
    @GetMapping("/calificaciones")
    public ResponseEntity<List<CalificacionLugarDTO>> listar() {
        return ResponseEntity.ok(calificacionService.listar());
    }

    // obtener por id
    @GetMapping("/calificaciones/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            var opt = calificacionService.obtenerPorId(id);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Calificación no encontrada"));
        } catch (Exception e) {
            log.error("Error al obtener calificación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // crear
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody CalificacionLugarDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(calificacionService.crear(dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al crear calificación: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear calificación", "detalle", e.getMessage()));
        }
    }

    // actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody CalificacionLugarDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            var opt = calificacionService.actualizar(id, dto);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Calificación no encontrada"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar calificación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar calificación", "detalle", e.getMessage()));
        }
    }

    // eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = calificacionService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Calificación no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Calificación eliminada correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar calificación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar calificación", "detalle", e.getMessage()));
        }
    }
}