package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Reserva;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ReservaDTO;
import org.springframework.http.ResponseEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Reserva.ReservaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiReserva")
public class ReservaController {

        @Autowired
        private ReservaServices reservaServices;

        // Obtener todas las reservas
        @GetMapping
        public ResponseEntity<List<ReservaDTO>> obtenerTodasLasReservas() {
            List<ReservaDTO> reservas = reservaServices.obtenerTodasLasReservas();
            return ResponseEntity.ok(reservas);
        }

        // Obtener reserva por id
        @GetMapping("/{id}")
        public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable Integer id) {
            return reservaServices.obtenerReservaPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        // Insertar nueva reserva
        @PostMapping
        public ResponseEntity<ReservaDTO> insertarReserva(@RequestBody ReservaEntities reserva) {
            ReservaDTO reservaGuardada = reservaServices.insertarReserva(reserva);
            return ResponseEntity.ok(reservaGuardada);
        }

        // Actualizar reserva
        @PutMapping("/{id}")
        public ResponseEntity<ReservaDTO> actualizarReserva(@PathVariable Integer id, @RequestBody ReservaEntities reserva) {
            try {
                ReservaDTO reservaActualizada = reservaServices.actualizarReserva(id, reserva);
                return ResponseEntity.ok(reservaActualizada);
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }
    }
