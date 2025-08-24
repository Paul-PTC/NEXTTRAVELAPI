package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.CalificacionLugarDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico.CalificacionLugarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionLugarController {

    @Autowired
    private CalificacionLugarService service;

    @PostMapping("/registraCalificacionr")
    public ResponseEntity<?> registrar(@Valid @RequestBody CalificacionLugarDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo registrar", "detalle", e.getMessage()));
        }
    }

    @GetMapping("/obtenerCalificacion")
    public ResponseEntity<List<CalificacionLugarDTO>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.ok(Map.of("status", "Eliminado con éxito"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró el registro"));
        }
    }
    @PutMapping("/modificarCalificacion/{id}")
    public ResponseEntity<?> modificarCalificacionLugar(
            @PathVariable Long id,
            @Valid @RequestBody CalificacionLugarDTO calificacionDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            CalificacionLugarDTO calificacionActualizada = service.actualizarCalificacion(id, calificacionDTO);
            return ResponseEntity.ok(calificacionActualizada);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "No encontrado", "mensaje", e.getMessage())
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Error interno", "detalle", e.getMessage())
            );
        }
    }

}

