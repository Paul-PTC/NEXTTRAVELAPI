package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.VehiculoController;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionVehiculoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.VehiculoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.VehiculoServices.VehiculoServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.data.GraphQlQueryByExampleAutoConfiguration;
import org.springframework.data.domain.Page;
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
@RequestMapping("/apivehiculo")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoServices vehiculoService;

    // listar
    @GetMapping("/vehiculos")
    public ResponseEntity<Page<VehiculoDTO>> getDataVehiculos(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        if (size <= 0  || size > 50){
            ResponseEntity.badRequest().body(Map.of(
                    "Status", "EL tamaño de la Pagina debe estar entre 1 y 50 "
            ));
            return ResponseEntity.ok(null);
        }
        Page<VehiculoDTO> vehiculos = vehiculoService.listar(page, size);
        if (vehiculos == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay Vehiculos Registrados."
            ));
        }
        return ResponseEntity.ok( vehiculos);
    }

    // obtener por id
    @GetMapping("/vehiculos/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<VehiculoDTO> opt = vehiculoService.obtenerPorId(id);
            if (opt.isPresent()) return ResponseEntity.ok(opt.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Vehículo no encontrado"));
        } catch (Exception e) {
            log.error("Error al obtener vehículo {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno", "detalle", e.getMessage()));
        }
    }

    // crear
    @PostMapping("/insertar")
    public ResponseEntity<?> crear(@Valid @RequestBody VehiculoDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            VehiculoDTO creado = vehiculoService.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            log.error("Error al crear vehículo: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al crear vehículo", "detalle", e.getMessage()));
        }
    }

    // actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody VehiculoDTO dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError err : result.getFieldErrors()) {
                errores.put(err.getField(), err.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            Optional<VehiculoDTO> actualizado = vehiculoService.actualizar(id, dto);
            if (actualizado.isPresent()) return ResponseEntity.ok(actualizado.get());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Vehículo no encontrado"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Vehículo no encontrado", "mensaje", e.getMessage()));
        } catch (Exception e) {
            log.error("Error al actualizar vehículo {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al actualizar vehículo", "detalle", e.getMessage()));
        }
    }

    // eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = vehiculoService.eliminar(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vehículo no encontrado"));
            }
            return ResponseEntity.ok(Map.of("mensaje", "Vehículo eliminado correctamente"));
        } catch (Exception e) {
            log.error("Error al eliminar vehículo {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al eliminar vehículo", "detalle", e.getMessage()));
        }
    }
}
