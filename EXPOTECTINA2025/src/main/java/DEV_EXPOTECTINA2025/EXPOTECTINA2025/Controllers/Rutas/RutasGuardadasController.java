package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RutasGuardadasDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas.RutasGuardadasService;
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

@RestController
@RequestMapping("/apirutasguardadas")
@RequiredArgsConstructor
@Slf4j
public class RutasGuardadasController {

    @Autowired
    private RutasGuardadasService rutasService;

    // GET: Obtener todas las rutas guardadas
    @GetMapping("/rutasguardadas")
    public ResponseEntity<List<RutasGuardadasDTO>> obtenerTodas() {
        List<RutasGuardadasDTO> rutas = rutasService.obtenerTodas();
        return ResponseEntity.ok(rutas);
    }


    // POST: Crear nueva ruta guardada
    @PostMapping("/Insertarrutasguardadas")
    public ResponseEntity<?> registrar(@Valid @RequestBody RutasGuardadasDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            RutasGuardadasDTO creada = rutasService.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Error al guardar", "mensaje", e.getMessage()));
        }
    }

    // PUT: Actualizar ruta guardada
    @PutMapping("/ActualizarRutasGuardadas/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody RutasGuardadasDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            RutasGuardadasDTO actualizada = rutasService.actualizar(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Ruta no encontrada", "mensaje", e.getMessage()));
        }
    }

    // DELETE: Eliminar ruta guardada
    @DeleteMapping("/EliminarRutasGuardadas/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = rutasService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("error", "Ruta no encontrada", "timestamp", Instant.now().toString()));
            }

            return ResponseEntity.ok(Map.of("mensaje", "Ruta eliminada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Error al eliminar", "mensaje", e.getMessage()));
        }
    }
}
