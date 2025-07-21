package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Empleados;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado.ItinerarioEmpleadoServices;
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
import java.util.Objects;

@RestController
@RequestMapping("/apiItinerarioEmpleados")
public class ItinerarioEmpleadoController {
    @Autowired
    private ItinerarioEmpleadoServices servicio;

    @GetMapping("/ItinerariosEmpleados")
    public List<ItinerarioEmpleadoDTO>ObtenerItinearios(){return servicio.getALLItinerarioEmpleado();};

    @PostMapping("/InsertarItineariosEmpleados")
    public ResponseEntity<Map<String, Object>>ResgistrarItinerarioEmpleado(@Valid @RequestBody ItinerarioEmpleadoDTO Itiemp, HttpServletRequest request){
        try{
            //Guardar ItinerarioEmpleados
            ItinerarioEmpleadoDTO res = servicio.InsertarItinerarioEmpleado(Itiemp);
            if (res == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Insercion Incorrecta",
                        "errrorType", "VALIDATION_ERROR",
                        "message","Datos de Itinerario invalidos"
                ));
            }return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status","sucess",
                    "data",res
            ));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar usuario",
                            "detail", e.getMessage()
                    ));
        }
    }
    @PutMapping("/ActualizarItinerario/{idItinerario}")
    public ResponseEntity<?> ActualizarItineario(
            @PathVariable Long idItinerario,
            @Valid @RequestBody ItinerarioEmpleadoDTO ItiEmpDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Long> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), Long.valueOf(error.getDefaultMessage())));
            return ResponseEntity.badRequest().body(errores);
        }
    try {
        ItinerarioEmpleadoDTO ItiEmpActualizado = servicio.ActualizaItinerarioEmpleado(idItinerario, ItiEmpDTO);
        return ResponseEntity.ok(ItiEmpActualizado);
    }
    catch (ExceptionsItinerarioEmpleadoNoEncontrado e){
        return ResponseEntity.notFound().build();
    }
        catch (ExcepcionDatosDuplicados e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
        );
    }
    }
    @PatchMapping("/ActualizarItinerario/{idItinerario}")
    public ResponseEntity<?> actualizarParcialItinerario(
            @PathVariable Long idItinerario,
            @RequestBody ItinerarioEmpleadoDTO ItiEmpDTO) {
        try {
            ItinerarioEmpleadoDTO actualizado = servicio.actualizarParcialItinerarioEmpleado(idItinerario, ItiEmpDTO);
            return ResponseEntity.ok(actualizado);
        } catch (ExceptionsItinerarioEmpleadoNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of(
                            "error", "Datos duplicados",
                            "campo", e.getCampoDuplicado()
                    )
            );
        }
    }
    @DeleteMapping("/EliminarItinerario/{idItinerario}")
    public ResponseEntity<Map<String,Object>>EliminarItinerario(@PathVariable Long idItinerario){
        try {
            if (!servicio.EliminarItinerarioEmpleado(idItinerario)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje de error", "Itinerario no encontrado")
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "El Itinerario no ha sido encontrado",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Itinerario eliminado exitosamente"  // Mensaje de éxito
            ));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar itinerario",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }
}
