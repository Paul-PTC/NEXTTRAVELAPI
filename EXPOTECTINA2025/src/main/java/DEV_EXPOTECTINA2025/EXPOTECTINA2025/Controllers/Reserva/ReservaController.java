package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Reserva;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ReservaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Reserva.ReservaServices;
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
@RequestMapping("/apireserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaServices reservaService;

    // listar
    @GetMapping("/reservas")
    public ResponseEntity<List<ReservaDTO>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    // obtener por id
    @GetMapping("/reservas/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            var opt = reservaService.obtenerPorId(id);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Reserva no encontrada"));
        } catch (Exception e) {
            log.error("Error al obtener reserva {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // crear
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody ReservaDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al crear reserva: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear reserva", "detalle", e.getMessage()));
        }
    }

    // actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody ReservaDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) errores.put(err.getField(), err.getDefaultMessage());
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            var opt = reservaService.actualizar(id, dto);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Reserva no encontrada"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar reserva {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar reserva", "detalle", e.getMessage()));
        }
    }

    // eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = reservaService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Reserva no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Reserva eliminada correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar reserva {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar reserva", "detalle", e.getMessage()));
        }
    }
}