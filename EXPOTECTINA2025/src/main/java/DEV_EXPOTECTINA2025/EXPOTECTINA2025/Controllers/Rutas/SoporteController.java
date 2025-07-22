package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionElementoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.SoporteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas.SoporteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiSoporte")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    @GetMapping("/soportes")
    public List<SoporteDTO> obtenerSoportes() {
        return soporteService.obtenerTodos();
    }

    @PostMapping("/ingresarSoporte")
    public ResponseEntity<Map<String, Object>> registrarSoporte(@Valid @RequestBody SoporteDTO soporte, HttpServletRequest request) {
        try {
            // Intento de guardar el soporte
            SoporteDTO respuesta = soporteService.insertSoporte(soporte);

            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del soporte inválidos"
                ));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", respuesta
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar soporte",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/modificarSoporte/{id}")
    public ResponseEntity<Map<String, Object>> modificarSoporte(
            @PathVariable Long id,
            @Valid @RequestBody SoporteDTO soporte,
            BindingResult bindingResult) {

        // Validaciones de campos
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "VALIDATION_ERROR",
                    "errors", errores
            ));
        }

        try {
            // Llamada al servicio para actualizar soporte
            SoporteDTO soporteActualizado = soporteService.actualizarSoporte(id, soporte);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "data", soporteActualizado
            ));

        } catch (ExcepcionElementoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", "Soporte no encontrado",
                    "detail", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error inesperado al modificar el soporte",
                    "detail", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/eliminarSoporte/{id}")
    public ResponseEntity<Map<String, Object>> eliminarSoporte(@PathVariable Long id) {
        try {
            // Intenta eliminar el soporte usando el servicio 'acceso'
            if (!soporteService.removerSoporte(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje-Error", "Soporte no encontrado")
                        .body(Map.of(
                                "error", "Not found",
                                "mensaje", "El soporte no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }

            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Soporte eliminado exitosamente"
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el soporte",
                    "detail", e.getMessage()
            ));
        }
    }

}

