package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Pago;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Pago.ServicesPago;
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
@RequestMapping("/Pago/Usuarios")
public class ControllerPago {
    @Autowired
    private ServicesPago services;


    @GetMapping("/ObtenerPagos")
    public List<DTOPago> ObtenerPagos(){return services.getAllPagos();}


    @PostMapping("/InsertarPagos")
    public ResponseEntity<Map<String, Object>> ResgistrarItinerarioEmpleado(@Valid @RequestBody DTOPago pago, HttpServletRequest request){
        try{
            //Guardar ItinerarioEmpleados
            DTOPago res = services.InsertarPagos(pago);
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

    @PutMapping("/ActualizarPagos/{idpago}")
    public ResponseEntity<?> ActualizarPagos(
            @PathVariable Long idpago,
            @Valid @RequestBody DTOPago dtoPago,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Long> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(errors ->
                    errores.put(errors.getField(), Long.valueOf(errors.getDefaultMessage())));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            DTOPago Actualizar = services.ActualizarPago(idpago, dtoPago);
            return ResponseEntity.ok(Actualizar);
        } catch (ExceptionsItinerarioEmpleadoNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }
    }

    @DeleteMapping("/EliminarItinerario/{id}")
    public ResponseEntity<Map<String,Object>>EliminarItinerario(@PathVariable Long id){
        try {
            if (!services.eliminarPago(id)){
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
