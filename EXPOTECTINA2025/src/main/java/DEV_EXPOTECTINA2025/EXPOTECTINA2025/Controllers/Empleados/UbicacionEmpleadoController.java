package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Empleados;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.UbicacionEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado.UbicacionEmpleadoService;
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
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/apiubicacion")
@RequiredArgsConstructor
public class UbicacionEmpleadoController {

    @Autowired
    private final UbicacionEmpleadoService ubicacionService;

    @GetMapping("/ubicaciones")
    public ResponseEntity<List<UbicacionEmpleadoDTO>> listar() {
        List<UbicacionEmpleadoDTO> lista = ubicacionService.getAll();
        return ResponseEntity.ok(lista);
    }

    // obtener por id
    @GetMapping("/ubicaciones/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<UbicacionEmpleadoDTO> opt = ubicacionService.obtenerPorId(id);
            if (opt.isPresent()) {
                return ResponseEntity.ok(opt.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ubicación no encontrada"));
        } catch (Exception e) {
            log.error("Error al obtener ubicación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // registrar ubicación
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@Valid @RequestBody UbicacionEmpleadoDTO dto,
                                       BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            UbicacionEmpleadoDTO creado = ubicacionService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al registrar ubicación: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al registrar ubicación", "detalle", e.getMessage()));
        }
    }

    // actualizar ubicación
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody UbicacionEmpleadoDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            Optional<UbicacionEmpleadoDTO> actualizado = ubicacionService.actualizar(id, dto);
            if (actualizado.isPresent()) {
                return ResponseEntity.ok(actualizado.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ubicación no encontrada"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Ubicación no encontrada", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar ubicación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar ubicación", "detalle", e.getMessage()));
        }
    }

    // eliminar ubicación
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = ubicacionService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Ubicación no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Ubicación eliminada correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar ubicación {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar ubicación", "detalle", e.getMessage()));
        }
    }
}
