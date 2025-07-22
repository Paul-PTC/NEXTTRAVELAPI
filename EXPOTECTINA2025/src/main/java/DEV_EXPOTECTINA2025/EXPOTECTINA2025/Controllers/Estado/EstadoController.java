package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Estado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EstadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Estado.EstadoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private EstadoServices estadoServices;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> obtenerTodos() {
        return ResponseEntity.ok(estadoServices.obtenerTodosEstados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> obtenerPorId(@PathVariable Integer id) {
        EstadoDTO dto = estadoServices.obtenerEstadoPorId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> insertar(@RequestBody EstadoDTO dto) {
        EstadoDTO creado = estadoServices.insertarEstado(dto);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> actualizar(@PathVariable Integer id, @RequestBody EstadoDTO dto) {
        try {
            EstadoDTO actualizado = estadoServices.actualizarEstado(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        estadoServices.eliminarEstado(id);
        return ResponseEntity.noContent().build();
    }
}
