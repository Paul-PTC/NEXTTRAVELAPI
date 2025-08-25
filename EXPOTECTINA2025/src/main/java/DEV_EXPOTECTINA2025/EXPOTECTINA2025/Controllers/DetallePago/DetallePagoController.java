package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.DetallePago;


import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.DetallePagoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsTipoMantenimientoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DetallePagoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EstadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TipoMantenimientoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.DetallePago.DetallePagoService;
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
@RequestMapping("/api/DetallePagos")
public class DetallePagoController {
    @Autowired
    private DetallePagoService services;

    @GetMapping("/DetallesPago")
    public ResponseEntity<List<DetallePagoDTO>> ObtenerDetallesPagos() {
        return ResponseEntity.ok(services.getAllDetallesPago());
    }

    @PostMapping("/InsertarDetallesdePago")
    public ResponseEntity<Map<String,Object>>InsertarDetallesPagos(@Valid @RequestBody DetallePagoDTO detalleDTO, HttpServletRequest request) {
        //Porceso para guardar datos
        try {
            DetallePagoDTO res = services.insertarDetallePagos(detalleDTO);
            if (res == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Insercion Incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos para insercion invalidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "succes",
                    "data", res
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar el tipo de mantenimiento ",
                            "detaill", e.getMessage()
                    ));
        }
    }

    @PutMapping("/EditarDetallesPago/{idDetallePago}")
    public ResponseEntity<?>EditarDetallepago(
            @PathVariable Long idDetallePago,
            @Valid @RequestBody DetallePagoDTO detalleDTO,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,Long> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), Long.valueOf(error.getDefaultMessage())));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
                DetallePagoDTO detallePagoDTO = new DetallePagoDTO();
                    services.actualizarDetallePago(idDetallePago, detalleDTO);
            return ResponseEntity.ok(detallePagoDTO);
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

    @DeleteMapping("/EliminarDetallePago/{idDetllePago}")
    public ResponseEntity <Map<String,Object>> EliminarDetallePago(@PathVariable Long idDetllePago){
        try{
            if (!services.EliminarDetallesPagos(idDetllePago)){
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
