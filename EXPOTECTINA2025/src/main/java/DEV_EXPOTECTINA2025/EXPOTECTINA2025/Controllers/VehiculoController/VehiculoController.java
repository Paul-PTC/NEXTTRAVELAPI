package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.VehiculoController;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionVehiculoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.VehiculoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.VehiculoServices.VehiculoServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.data.GraphQlQueryByExampleAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiVehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoServices servicio;

    @GetMapping("/vehiculos")
    public List<VehiculoDTO> obtenerVehiculos(){return  servicio.obtenerTodo();}

    @PostMapping("/insertarVehiculos")
    public ResponseEntity<?> nuevoVehiculo(@Valid @RequestBody VehiculoDTO json, HttpServletRequest request){
        try {
            VehiculoDTO res = servicio.insertarVehiculo(json);
            if (res == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status","Insercion Fallida",
                        "errorType", "VALIDACION_ERROR",
                        "message", "Los datos no pudieron ser ingresados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Success",
                    "data", res
            ));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Insercion fallida",
                    "message", "Error no controlado",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/editarVehiculo/{idVehiculo}")
    public ResponseEntity<?> modificarVehiculo(
            @PathVariable Long idVehiculo,
            @Valid @RequestBody VehiculoDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            //Crear un mapa de errores
            Map<String, String> errores = new HashMap<>();
            //Iterar sobre cada error y lo agregamos al objeto map
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            //Insertar actualizar el ususario llamado al servicio
            VehiculoDTO dto = servicio.actualizarVehiculo(idVehiculo, json);
            return ResponseEntity.ok(dto);
        }catch (ExceptionVehiculoNoEncontrado e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/eliminarVehiculo/{idVehiculo}")
    public ResponseEntity<?> eliminarVehiculo(@PathVariable Long idVehiculo) {
        try {
            String mensaje = servicio.eliminarVehiculo(idVehiculo);
            return ResponseEntity.ok(mensaje);
        } catch (ExceptionVehiculoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vehículo no encontrado con ID: " + idVehiculo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el vehículo: " + e.getMessage());
        }
    }
}
