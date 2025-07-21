package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Mantenimiento;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsTipoMantenimientoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TipoMantenimientoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado.TipoMantenimientoServices;
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
@RequestMapping("/apiTipoMantenimiento")
public class TipoMantenimientoController {
    @Autowired
    private TipoMantenimientoServices servicio;

    @GetMapping("/TipoMantenimient0")
    public List<TipoMantenimientoDTO>ObtenerTipoMantenimiento(){
        return servicio.getAllTipoMantenimiento();
    }

    //Insercion de datos
    @PostMapping("/InsertarTipoMantenimiento")
    public ResponseEntity<Map<String,Object>>ResgistrarTipoMantenimiento(@Valid @RequestBody TipoMantenimientoDTO tipomantenimiento, HttpServletRequest request){
        //Porceso para guardar datos
        try {
            TipoMantenimientoDTO res = servicio.insertarTipoMantenimiento(tipomantenimiento);
            if (res == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Insercion Incorrecta",
                        "errorType","VALIDATION_ERROR",
                        "message","Datos para insercion invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status","succes",
                    "data",res
            ));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status","error",
                            "message","Error al registrar el tipo de mantenimiento ",
                            "detaill", e.getMessage()
                    ));
        }
    }

    @PutMapping("/EditarTipodeMantenimiento/{idTipoMantenimiento}")
    public ResponseEntity<?>EditarTipodeMantenimiento(
            @PathVariable Long idTipoMantenimiento,
            @Valid @RequestBody TipoMantenimientoDTO mantenimientoDTO,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,Long> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), Long.valueOf(error.getDefaultMessage())));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            TipoMantenimientoDTO TipoMantenimientoDTO = new TipoMantenimientoDTO();
            TipoMantenimientoDTO TipoMantenimientoActualizado =
                    servicio.actualizarTipoMantenimientos(idTipoMantenimiento, TipoMantenimientoDTO);
            return ResponseEntity.ok(TipoMantenimientoActualizado);
        }
        catch (ExceptionsTipoMantenimientoNoEncontrado e){
            return ResponseEntity.notFound().build();
        }
        catch (ExcepcionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }
    }
    @PatchMapping("/ActualizarTipoMantenimiento/{idTipoMantenimiento}")
    public ResponseEntity<?> actualizarTipoMantenimientoParcial(
            @PathVariable Long idTipoMantenimiento,
            @RequestBody TipoMantenimientoDTO tipoMantenimientoDTO) {

        try {
            TipoMantenimientoDTO actualizado = servicio.actualizarTipoMantenimiento(idTipoMantenimiento, tipoMantenimientoDTO);
            return ResponseEntity.ok(actualizado);

        } catch (ExceptionsTipoMantenimientoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Tipo de mantenimiento no encontrado", "id", idTipoMantenimiento));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar el tipo de mantenimiento", "detalle", e.getMessage()));
        }
    }
    @DeleteMapping("/EliminarTipoMantenimiento/{idTipoMantenimineto}")
    public ResponseEntity <Map<String,Object>> EliminarTipoMantenimiento(@PathVariable Long idTipoMantenimineto){
        try{
            if (!servicio.EliminarTipoMantenimineto(idTipoMantenimineto)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-mensaje de error","Resgitro no encontrado")
                        .body(Map.of(
                                "error","Not found",
                                "mensaje", "El Tipo de Mantenimineto no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status","Proceso compleatado",
                    "message","Tipo de mantenimiento eliminado existosamente"
            ));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status","Error",
                    "message","Error al eliminar el tipo de mantenimineto",
                    "detail", e.getMessage()
            ));
        }
    }
}
