package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.EstadoViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EstadoViajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.EstadoViaje.EstadoViajeServices;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/estadoviaje")
@Slf4j
public class EstadoViajeController {

    @Autowired
    private EstadoViajeServices estadoViajeService;

    // GET: Listar todos los estados de viaje
    @GetMapping("/listar")
    public ResponseEntity<List<EstadoViajeDTO>> listarTodos() {
        return ResponseEntity.ok(estadoViajeService.obtenerTodos());
    }

    // POST: Crear nuevo estado de viaje
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody EstadoViajeDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            EstadoViajeDTO creado = estadoViajeService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al crear EstadoViaje: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Error al crear", "mensaje", e.getMessage()));
        }
    }

    // PUT: Actualizar estado de viaje
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody EstadoViajeDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            EstadoViajeDTO actualizado = estadoViajeService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Estado de viaje no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Error al actualizar", "mensaje", e.getMessage()));
        }
    }

    // DELETE: Eliminar estado de viaje
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = estadoViajeService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("error", "Estado de viaje no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Estado de viaje eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Error al eliminar", "mensaje", e.getMessage()));
        }
    }
}
