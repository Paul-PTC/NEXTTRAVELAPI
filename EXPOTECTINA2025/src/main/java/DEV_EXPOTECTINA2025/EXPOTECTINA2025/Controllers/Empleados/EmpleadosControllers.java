package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Empleados;


import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado.EmpleadoServices;
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
@RequestMapping("/apiEmpleados")
public class EmpleadosControllers {
    @Autowired
    private EmpleadoServices servicio;

    @GetMapping("/empleados")
    public List<EmpleadoDTO> obtenerEmpleados() {
        return servicio.getAllEmpleados();
    }

    @PostMapping("/insertarEmpleados")
    public ResponseEntity<Map<String, Object>> registrarEmpleado(@Valid @RequestBody EmpleadoDTO empleado, HttpServletRequest request){
        try {
            //Intentamos guardar empleados
            EmpleadoDTO respuesta = servicio.insertarEmpleado(empleado);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del usuario inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status","sucess",
                    "data",respuesta
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

    @PutMapping("/modificarEmpleado/{dui}")
    public ResponseEntity<?> modicarEmpleado(
            @PathVariable String dui,
            @Valid @RequestBody EmpleadoDTO empleadoDTO,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            EmpleadoDTO EmpleadoActualizado = servicio.actualizarEmpleados(dui, empleadoDTO);
            return ResponseEntity.ok(EmpleadoActualizado);
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

    @DeleteMapping("/eliminarEmpleado/{dui}")
    public ResponseEntity<Map<String, Object>> eliminarEmpleado(@PathVariable String dui) {
        try {
            if (!servicio.EliminarUsuario(dui)){
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
