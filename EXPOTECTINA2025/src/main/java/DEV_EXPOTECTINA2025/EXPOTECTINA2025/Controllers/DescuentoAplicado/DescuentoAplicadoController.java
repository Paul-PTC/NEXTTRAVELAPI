package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.DescuentoAplicado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DescuentoAplicadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.DescuentoAplicado.DescuentoAplicadoServices;
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
@RequestMapping("/api/DescuentoAplicado")
public class DescuentoAplicadoController {
    @Autowired
    private DescuentoAplicadoServices services;

    @GetMapping("/ObtenerDescuento")
    public List<DescuentoAplicadoDTO> obtenerEmpleados() {
        return services.getAllDescuento();
    }

    @PostMapping("/InsertarDescuentos")
    public ResponseEntity<Map<String, Object>> RegistrarDescuentos(@Valid @RequestBody DescuentoAplicadoDTO descuento, HttpServletRequest request){
        try {
            //Intentamos guardar empleados
            DescuentoAplicadoDTO respuesta = services.InsertarDescuentoAplicado(descuento);
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

    @PutMapping("/modificarDescuentoAplicado/{idDescuento}")
    public ResponseEntity<?> ModificarDescuentoAplicado(
            @PathVariable Long idDescuento,
            @Valid @RequestBody DescuentoAplicadoDTO descuentoDTO,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            DescuentoAplicadoDTO Descuentoactualizado = services.actualizarEmpleados(idDescuento, descuentoDTO);
            return ResponseEntity.ok(Descuentoactualizado);
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

    @DeleteMapping("/EliminarDescuentoAplicado/{id}")
    public ResponseEntity<Map<String, Object>> EliminarDescuentoAplicado(@PathVariable Long id) {
        try {
            if (!services.EliminarDescuentosAplicados(id)){
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
