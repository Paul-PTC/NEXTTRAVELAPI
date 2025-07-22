package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.EstadoViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EstadoViajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.EstadoViaje.EstadoViajeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Apiestadoviaje")
public class EstadoViajeController {
    @Autowired
    private EstadoViajeServices estadoViajeServices;

    @GetMapping
    public ResponseEntity<List<EstadoViajeDTO>> obtenerTodos() {
        return ResponseEntity.ok(estadoViajeServices.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoViajeDTO> obtenerPorId(@PathVariable Integer id) {
        EstadoViajeDTO dto = estadoViajeServices.obtenerPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EstadoViajeDTO> insertar(@RequestBody EstadoViajeDTO dto) {
        return ResponseEntity.ok(estadoViajeServices.insertar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoViajeDTO> actualizar(@PathVariable Integer id, @RequestBody EstadoViajeDTO dto) {
        try {
            return ResponseEntity.ok(estadoViajeServices.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        estadoViajeServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
