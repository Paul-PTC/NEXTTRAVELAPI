package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.EstadoViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TiempoEstimadoLlegadaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.EstadoViaje.TiempoEstimadoLlegadaService;
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
@RequestMapping("/apitiempo")
@RequiredArgsConstructor
public class TiempoEstimadoLlegadaController {

    private TiempoEstimadoLlegadaService tiempoService;

    // listar
    @GetMapping("/tiempos")
    public ResponseEntity<List<TiempoEstimadoLlegadaDTO>> listar() {
        return ResponseEntity.ok(tiempoService.listar());
    }

    // obtener por id
    @GetMapping("/tiempos/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return tiempoService.obtenerPorId(id)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Tiempo estimado no encontrado")));
        } catch (Exception e) {
            log.error("Error al obtener tiempo estimado {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // crear
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody TiempoEstimadoLlegadaDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            TiempoEstimadoLlegadaDTO creado = tiempoService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al crear tiempo estimado: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear tiempo estimado", "detalle", e.getMessage()));
        }
    }

    // actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody TiempoEstimadoLlegadaDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            return tiempoService.actualizar(id, dto)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Tiempo estimado no encontrado")));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tiempo estimado no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar tiempo estimado {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar tiempo estimado", "detalle", e.getMessage()));
        }
    }

    // eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = tiempoService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Tiempo estimado no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Tiempo estimado eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar tiempo estimado {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar tiempo estimado", "detalle", e.getMessage()));
        }
    }
}
