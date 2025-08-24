package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Reserva;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ReservaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Reserva.ReservaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiReserva")
public class ReservaController {

    @Autowired
    private ReservaServices reservaServices;

    // Obtener todas las reservas
    @GetMapping("/obtenerReservas")
    public ResponseEntity<List<ReservaDTO>> obtenerTodasLasReservas() {
        List<ReservaDTO> reservas = reservaServices.obtenerTodasLasReservas();
        return ResponseEntity.ok(reservas);
    }

    // Obtener reserva por id
    @GetMapping("RutasconId/{id}")
    public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable Long id) {
        return reservaServices.obtenerReservaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Insertar nueva reserva
    @PostMapping("insertarRutas")
    public ResponseEntity<ReservaDTO> insertarReserva(@RequestBody ReservaEntities reserva) {
        ReservaDTO reservaGuardada = reservaServices.insertarReserva(reserva);
        return ResponseEntity.ok(reservaGuardada);
    }

    // Actualizar reserva
    @PutMapping("actualizarRuta/{id}")
    public ResponseEntity<ReservaDTO> actualizarReserva(@PathVariable Long id, @RequestBody ReservaEntities reserva) {
        try {
            ReservaDTO reservaActualizada = reservaServices.actualizarReserva(id, reserva);
            return ResponseEntity.ok(reservaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("EliminarRuta/{id}")
    public ResponseEntity<Map<String, Object>> eliminarReserva(@PathVariable Long id) {
        try {
            if (!reservaServices.eliminarReserva(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensaje de error", "Ruta no encontrada")
                        .body(Map.of(
                                "error", "Not found",  // Tipo de error
                                "mensaje", "la ruta no ha sido encontrado",  // Mensaje descriptivo
                                "timestamp", Instant.now().toString()  // Marca de tiempo del error
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",  // Estado de la operación
                    "message", "Rango eliminado exitosamente"  // Mensaje de éxito
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el usuario",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }
}