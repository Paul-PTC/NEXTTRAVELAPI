package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Cliente;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ClienteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Cliente.ClienteServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
@RestController
@RequestMapping("/apiCliente")
public class ClienteController {
    @Autowired
    private ClienteServices clienteService;

    @GetMapping("/clientes")
    public List<ClienteDTO> obtenerClientes() {
        return clienteService.getAllEmpleados();
    }

    @PostMapping("/clientesInsertar")
    public ResponseEntity<?> crearCliente(@Valid @RequestBody ClienteDTO clienteDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            ClienteEntities nuevoCliente = clienteService.insertarCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("mensaje", "Error al registrar cliente", "detalle", e.getMessage())
            );
        }
    }

    @PutMapping("/clientesActualizar/{dui}")
    public ResponseEntity<?> actualizarCliente(@PathVariable String dui,
                                               @Valid @RequestBody ClienteDTO clienteDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            ClienteDTO actualizado = clienteService.actualizarCliente(dui, clienteDTO);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cliente no encontrado", "detalle", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al actualizar cliente", "detalle", e.getMessage()));
        }
    }

    @DeleteMapping("eliminarClientes/{dui}")
    public ResponseEntity<?> eliminarCliente(@PathVariable String dui) {
        boolean eliminado = clienteService.eliminarCliente(dui);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cliente no encontrado"));
        }

        return ResponseEntity.ok(Map.of("mensaje", "Cliente eliminado exitosamente"));
    }
}
