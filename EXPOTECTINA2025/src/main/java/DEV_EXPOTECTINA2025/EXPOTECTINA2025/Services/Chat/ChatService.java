package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ChatEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ChatDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ChatRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepo;

    @Autowired
    private ClienteRepository clienteRepo;   // ID = String (DUI)

    @Autowired
    private EmpleadoRepository empleadoRepo; // ID = String (DUI)

    // LISTAR
    public List<ChatDTO> obtenerTodos() {
        return chatRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public ChatDTO obtenerPorId(Long id) {
        ChatEntities e = chatRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el chat con ID: " + id));
        return toDTO(e);
    }

    // CREAR
    public ChatDTO registrar(@Valid ChatDTO dto) {
        ClienteEntities cliente = clienteRepo.findById(dto.getDuiCliente())
                .orElseThrow(() -> new EntityNotFoundException("No existe Cliente con DUI: " + dto.getDuiCliente()));

        EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleado()));

        ChatEntities e = new ChatEntities();
        e.setIdChat(null); // asegurar inserción (si usas secuencia)
        e.setCliente(cliente);
        e.setEmpleado(empleado);
        // Si dto.getFechaInicio() es null, dejamos que la DB aplique DEFAULT CURRENT_TIMESTAMP
        if (dto.getFechaInicio() != null) e.setFechaInicio(dto.getFechaInicio());
        e.setEstado(dto.getEstado());
        e.setUltimoMensaje(dto.getUltimoMensaje());

        e = chatRepo.save(e);
        return toDTO(e);
    }

    // ACTUALIZAR
    public ChatDTO actualizar(Long id, @Valid ChatDTO dto) {
        ChatEntities e = chatRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el chat con ID: " + id));

        ClienteEntities cliente = clienteRepo.findById(dto.getDuiCliente())
                .orElseThrow(() -> new EntityNotFoundException("No existe Cliente con DUI: " + dto.getDuiCliente()));
        EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleado()));

        e.setCliente(cliente);
        e.setEmpleado(empleado);
        // Mantén la fechaInicio existente si no viene en el DTO
        if (dto.getFechaInicio() != null) e.setFechaInicio(dto.getFechaInicio());
        e.setEstado(dto.getEstado());
        e.setUltimoMensaje(dto.getUltimoMensaje());

        e = chatRepo.save(e);
        return toDTO(e);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        if (!chatRepo.existsById(id)) return false;
        chatRepo.deleteById(id);
        return true;
    }

    // ====== MAPPER ======
    private ChatDTO toDTO(ChatEntities e) {
        ChatDTO d = new ChatDTO();
        d.setIdChat(e.getIdChat());
        d.setFechaInicio(e.getFechaInicio());
        d.setEstado(e.getEstado());
        d.setUltimoMensaje(e.getUltimoMensaje());
        if (e.getCliente() != null) d.setDuiCliente(e.getCliente().getDuiCliente());
        if (e.getEmpleado() != null) d.setDuiEmpleado(e.getEmpleado().getDuiEmpleado());
        return d;
    }
}
