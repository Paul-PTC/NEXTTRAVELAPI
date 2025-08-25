package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ChatEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ChatDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ChatRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public ChatDTO registrar(@Valid ChatDTO dto) {
        try {
            // Validaciones rápidas para evitar "The given id must not be null"
            if (dto == null) throw new IllegalArgumentException("El cuerpo (dto) es obligatorio");
            if (dto.getDuiCliente() == null || dto.getDuiCliente().isBlank())
                throw new IllegalArgumentException("duiCliente es obligatorio");
            if (dto.getDuiEmpleado() == null || dto.getDuiEmpleado().isBlank())
                throw new IllegalArgumentException("duiEmpleado es obligatorio");

            // FKs
            ClienteEntities cliente = clienteRepo.findById(dto.getDuiCliente())
                    .orElseThrow(() -> new EntityNotFoundException("No existe Cliente con DUI: " + dto.getDuiCliente()));
            EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleado())
                    .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleado()));

            // Mapear entidad
            ChatEntities nuevo = new ChatEntities();
            nuevo.setIdChat(null); // fuerza INSERT con secuencia
            nuevo.setCliente(cliente);
            nuevo.setEmpleado(empleado);
            nuevo.setEstado(dto.getEstado()); // puede ser null si tu lógica lo permite

            // Si querés respetar el DEFAULT CURRENT_TIMESTAMP de la DB, no seteés cuando venga null
            if (dto.getFechaInicio() != null) {
                nuevo.setFechaInicio(dto.getFechaInicio());
            }
            nuevo.setUltimoMensaje(dto.getUltimoMensaje()); // puede ser null

            // Guardar
            ChatEntities guardado = chatRepo.save(nuevo);

            // Devolver lo persistido en formato DTO
            return toDTO(guardado);
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            // Re-lanza excepciones "esperadas" para que el controller las traduzca a 400/404
            throw ex;
        } catch (Exception e) {
            // Logueá y envolvé para no devolver null silencioso
            log.error("Error registrando chat", e);
            throw new RuntimeException("No se pudo registrar el chat", e);
        }
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
