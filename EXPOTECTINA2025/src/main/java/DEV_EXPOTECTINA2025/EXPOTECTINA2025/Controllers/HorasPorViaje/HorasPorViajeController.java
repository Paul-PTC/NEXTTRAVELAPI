package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.HorasPorViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsHorasPorViajeNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.HorasPorViajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.HoraPorViaje.HoraPorViajeServices;
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
@RequestMapping("/apiHorasPorViaje")
public class HorasPorViajeController {
    @Autowired
    private HoraPorViajeServices servicio;

    @GetMapping("/HorasPorViaje")
    public List<HorasPorViajeDTO> ObtenerHorasPorViaje() {
        return servicio.getAllHorasPorViaje();
    }

    @PostMapping("/InsertaHorasViaje")
    public ResponseEntity<Map<String, Object>> registrarHoras(
            @Valid @RequestBody HorasPorViajeDTO horasViaje,
            BindingResult result,
            HttpServletRequest request) {

        // Validación de campos
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(Map.of(
                    "status", "Inserción Incorrecta",
                    "errorType", "VALIDATION_ERROR",
                    "message", "Datos de horas del viaje inválidos",
                    "errores", errores
            ));
        }

        try {
            // Guardar
            HorasPorViajeDTO res = servicio.InsertarHorasPorViaje(horasViaje);

            if (res == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción Incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "No se pudo insertar el registro de horas del viaje"
                ));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "message", "Registro de horas del viaje insertado correctamente",
                    "data", res
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar las horas del viaje",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/ActualizarHoras/{idHorasViaje}")
    public ResponseEntity<?> ActualizarHoras(
            @PathVariable Long idHorasViaje,
            @Valid @RequestBody HorasPorViajeDTO HorasDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            HorasPorViajeDTO HorasActualizado = servicio.ActualizarHorasPorViaje(idHorasViaje, HorasDTO);
            return ResponseEntity.ok(HorasActualizado);
        } catch (ExceptionsHorasPorViajeNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );

        }
    }

    @DeleteMapping("/EliminarHoras/{idHorasViaje}")
    public ResponseEntity<Map<String,Object>>EliminarHoras(@PathVariable Long idHorasViaje){
        try {
            if (!servicio.EliminarHorasPorViaje(idHorasViaje)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje de error", "Itinerario no encontrado")
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "Las busqueda ha sido encontrada",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Horas eliminadas exitosamente"  // Mensaje de éxito
            ));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar Horas",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }
}



