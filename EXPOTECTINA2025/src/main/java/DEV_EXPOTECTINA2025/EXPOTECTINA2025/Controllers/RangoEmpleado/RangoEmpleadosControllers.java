package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.RangoEmpleado;


import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.RangoEmpleado.RangoEmpleadoServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiRangoEmpledos")
public class RangoEmpleadosControllers {
    @Autowired
    private RangoEmpleadoServices accesoRango;

    @GetMapping("/RangoEmpleados")
    public List<RangoDTO> datosRango(){
        return accesoRango.getAllRangoEmpleado();
    }

    @PostMapping("/insertarRangoEmpleado")
    public ResponseEntity<Map<String, Object>> registrarRangoEmpleado(@Valid @RequestBody RangoDTO rangoEmpleado, HttpServletRequest request){
        try {
            //Intentamos guardar los datos
            RangoDTO respuesta = accesoRango.insertarRangoEmpleado(rangoEmpleado);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del rango inválidos"
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
    @PutMapping('/ActRangoEmp/{id}')
    public ResponseEntity<?> modificarRangoEmpleado(
            @PathVariable String id,
            @Valid @RequestBody RangoDTO rangoDTO,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            RangoDTO RangoActualizado = accesoRango.actualizarRangoEmpleado(id, rangoDTO);
            return ResponseEntity.ok(RangoActualizado);
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

}
