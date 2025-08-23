package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.TiempoEstimadoLleagada;


import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TiempoLlegadaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.TiempoEstiamdoLlegada.TiempoLlegadaService;
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
@RequestMapping("/api/tiempoEstimado")
public class TiemporEstimadoLLegadaController {

    @Autowired
    private TiempoLlegadaService services;

    @GetMapping("/ObtenerTiempo")
    public List<TiempoLlegadaDTO> ObtenerTiempoLlegada(){return services.getAllTiempos();}


    @PostMapping("/InsertarTiempos")
    public ResponseEntity<Map<String, Object>> RegistrarTiempoLlegada(@Valid @RequestBody TiempoLlegadaDTO tiempo, HttpServletRequest request) {
        try {
            //Guardar ItinerarioEmpleados
            TiempoLlegadaDTO res = services.InsertarTiempoLlegada(tiempo);
            if (res == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Insercion Incorrecta",
                        "errrorType", "VALIDATION_ERROR",
                        "message", "Datos de Itinerario invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "sucess",
                    "data", res
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar usuario",
                            "detail", e.getMessage()
                    ));
        }
    }



    @PutMapping("/ActualizarTiempo/{idtiempo}")
    public ResponseEntity<?> ActualizarTiemposLLegada(
            @PathVariable Long idtiempo,
            @Valid @RequestBody TiempoLlegadaDTO dtoTiempo,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Long> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(errors ->
                    errores.put(errors.getField(), Long.valueOf(errors.getDefaultMessage())));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            TiempoLlegadaDTO Actualizar = services.ActualizarTiempoLLegada(idtiempo, dtoTiempo);
            return ResponseEntity.ok(Actualizar);
        } catch (ExceptionsItinerarioEmpleadoNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }
    }


    @DeleteMapping("/EliminarTiempoLlegada/{idtiempo}")
    public ResponseEntity<Map<String,Object>>ElimianrTiempoLlegada(@PathVariable Long idtiempo){
        try {
            if (!services.EliminarTiempoLlegada(idtiempo)){
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
