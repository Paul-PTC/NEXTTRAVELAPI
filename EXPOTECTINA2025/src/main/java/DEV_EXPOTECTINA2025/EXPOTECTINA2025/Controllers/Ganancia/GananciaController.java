package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Ganancia;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.GananciaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Ganancia.GananciaService;
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
@RequestMapping("/apiganancia")
@RequiredArgsConstructor
public class GananciaController {

    private final GananciaService gananciaService;

    // GET: obtener todas las ganancias
    @GetMapping("/ganancias")
    public ResponseEntity<List<GananciaDTO>> obtenerTodas() {
        List<GananciaDTO> ganancias = gananciaService.obtenerTodas();
        return ResponseEntity.ok(ganancias);
    }

    // GET: obtener ganancia por ID
    @GetMapping("/ganancias/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            GananciaDTO dto = gananciaService.obtenerPorId(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ganancia no encontrada", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al obtener ganancia {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: registrar ganancia
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@Valid @RequestBody GananciaDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            GananciaDTO creada = gananciaService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (Exception e) {
            log.error("Error al registrar ganancia: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al registrar ganancia", "detalle", e.getMessage()));
        }
    }

    // PUT: actualizar ganancia
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody GananciaDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            GananciaDTO actualizada = gananciaService.actualizar(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ganancia no encontrada", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar ganancia {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar ganancia", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar ganancia
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = gananciaService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Ganancia no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Ganancia eliminada correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar ganancia {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar ganancia", "detalle", e.getMessage()));
        }
    }
}

