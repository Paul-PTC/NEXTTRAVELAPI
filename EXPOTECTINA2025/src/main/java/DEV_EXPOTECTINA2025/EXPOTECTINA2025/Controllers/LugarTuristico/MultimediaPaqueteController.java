package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.MultimediaPaqueteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico.MultimediaPaqueteService;
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
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/apimultimedia")
@RequiredArgsConstructor
public class MultimediaPaqueteController {

    private MultimediaPaqueteService multimediaService;

    // GET: listar todo
    @GetMapping("/multimedia")
    public ResponseEntity<List<MultimediaPaqueteDTO>> obtenerTodos() {
        List<MultimediaPaqueteDTO> lista = multimediaService.listar();
        return ResponseEntity.ok(lista);
    }

    // GET: por id
    @GetMapping("/multimedia/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<MultimediaPaqueteDTO> opt = multimediaService.obtenerPorId(id);
            if (opt.isPresent()) {
                return ResponseEntity.ok(opt.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Multimedia no encontrada"));
        } catch (Exception e) {
            log.error("Error al obtener multimedia {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // POST: crear
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@Valid @RequestBody MultimediaPaqueteDTO dto,
                                       BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            MultimediaPaqueteDTO creado = multimediaService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al registrar multimedia: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al registrar multimedia", "detalle", e.getMessage()));
        }
    }

    // PUT: actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody MultimediaPaqueteDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            Optional<MultimediaPaqueteDTO> actualizado = multimediaService.actualizar(id, dto);
            if (actualizado.isPresent()) {
                return ResponseEntity.ok(actualizado.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Multimedia no encontrada"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Multimedia no encontrada", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar multimedia {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar multimedia", "detalle", e.getMessage()));
        }
    }

    // DELETE: eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = multimediaService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Multimedia no encontrada"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Multimedia eliminada correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar multimedia {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar multimedia", "detalle", e.getMessage()));
        }
    }
}
