package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.LugarTuristicoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico.LugarTuristicoServices;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.RangoEmpleado.RangoEmpleadoServices;
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
@RequestMapping("/apiLugarTuristico")
public class LugarTuristicoControllers {
    @Autowired
    private LugarTuristicoServices ServicesLugar;

    @GetMapping("/LugarTuristico")
    public List<LugarTuristicoDTO> DatosLugar(){
        return ServicesLugar.getAllLugarTuristico();
    }

    @PostMapping("/insertarLugarTuristico")
    public ResponseEntity<Map<String, Object>> registrarLugarTuristico(@Valid @RequestBody LugarTuristicoDTO LugarTuristico, HttpServletRequest request){
        try {
            //Intentamos guardar los datos
            LugarTuristicoDTO respuesta = ServicesLugar.insertarLugarTuristico(LugarTuristico);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del lugar inválidos"
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
                            "message", "Error al registrar lugar",
                            "detail", e.getMessage()
                    ));
        }
    }
    @PutMapping("/ActualizarLugarTuristico/{idLugar}")
    public ResponseEntity<?> modificarLugarTuristico(
            @PathVariable Long idLugar,
            @Valid @RequestBody LugarTuristicoDTO lugarDTO,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            LugarTuristicoDTO LugarActualizado = ServicesLugar.actualizarLugarTuristico(lugarDTO.getIdLugar(), lugarDTO);
            return ResponseEntity.ok(LugarActualizado);
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

    @DeleteMapping("EliminarRangoEmp/{id}")
    public ResponseEntity<Map<String, Object>> eliminarLugarTuristico(@PathVariable Long idLugar){
        try {
            if (!ServicesLugar.EliminarLugarTuristico(idLugar)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje de error", "Rango de empleado no encontrado")
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "El lugar no ha sido encontrado",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "lugar eliminado exitosamente"  // Mensaje de éxito
            ));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el lugar",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }

    }
}
