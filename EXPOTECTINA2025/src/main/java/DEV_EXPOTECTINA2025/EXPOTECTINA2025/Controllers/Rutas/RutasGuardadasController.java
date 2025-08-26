package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RutasGuardadasDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas.RutasGuardadasService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/apirutasguardadas")
@RequiredArgsConstructor
public class RutasGuardadasController {

    private final RutasGuardadasService rutasGuardadasService;

    // listar
    @GetMapping("/rutas")
    public ResponseEntity<List<RutasGuardadasDTO>> listar() {
        return ResponseEntity.ok(rutasGuardadasService.listar());
    }

    // obtener por id
    @GetMapping("/rutas/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            var opt = rutasGuardadasService.obtenerPorId(id);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ruta guardada no encontrada"));
        } catch (Exception e) {
            log.error("Error al obtener ruta guardada {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // crear
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody RutasGuardadasDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(rutasGuardadasService.crear(dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al crear ruta guardada: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear ruta guardada", "detalle", e.getMessage()));
        }
    }

    // actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody RutasGuardadasDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            var opt = rutasGuardadasService.actualizar(id, dto);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ruta guardada no encontrada"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar ruta guardada {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar ruta guardada", "detalle", e.getMessage()));
        }
    }

    // eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = rutasGuardadasService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Ruta guardada no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Ruta guardada eliminada correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar ruta guardada {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar ruta guardada", "detalle", e.getMessage()));
        }
    }
}
