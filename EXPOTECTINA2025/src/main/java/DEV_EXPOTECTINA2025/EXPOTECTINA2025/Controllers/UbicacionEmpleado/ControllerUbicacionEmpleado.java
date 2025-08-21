package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.UbicacionEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesUbicacionEmpleado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOUbicacionempleado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.UbicacionEmpleado.ServiceUbicacionEmpleado;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
@RequestMapping("/api/Ubicacion")
public class ControllerUbicacionEmpleado {

    @Autowired
    private ServiceUbicacionEmpleado service;

    @GetMapping("/ObtenerUbicacion")
    public List<DTOUbicacionempleado> ObtenerEmpleado(){ return service.GetAllUbicacions();}

    @PostMapping("/InsertarUbicacionEmpleados")
    public ResponseEntity<Map<String, Object>>RegistrarUbicacion(@Valid @RequestBody DTOUbicacionempleado ubicacion, HttpServletRequest request){
        try{
            DTOUbicacionempleado res = service.InsertarUbicacionEmpleado(ubicacion);
            //Si la respuesta da Null entonces mandamos un BadRequest();
            if (res == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Insercion Incorrecta en Ubicaciones",
                        "errorType", "INSERT_ERROR_UBICATIONS",
                        "message", "Datos de Insercion invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "succes",
                    "data", res
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error interno",
                    "message", "Error al registrar la Ubicacion",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/ActualizarUbicacion/{id}")
    public ResponseEntity<?> modificarUbicacion(
            @PathVariable Long id,
            @Valid @RequestBody DTOUbicacionempleado ubicacion,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            DTOUbicacionempleado UbicacionUpdate = service.actualizarEmpleadoUbicaacion(id, ubicacion);
            return ResponseEntity.ok(UbicacionUpdate);
        }
        catch (ExceptionsUsuarioNoEncontrado e){
            return ResponseEntity.notFound().build();
        }
        catch (ExcepcionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }
    }

    @DeleteMapping("/EliminarUbicacion/{id}")
    public ResponseEntity<Map<String, Object>> EliminarUbicacion(@PathVariable Long id) {
        try {
            if (!service.EliminarUbicacion(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje de error", "empleado no encontrado")
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "El empleado no ha sido encontrado",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Empleado eliminado exitosamente"  // Mensaje de éxito
            ));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el empleados",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }




}