package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ChatEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.MensajeEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.MensajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ChatRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.MensajeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepo;

    @Autowired
    private ChatRepository chatRepo;

    @Autowired
    private ClienteRepository clienteRepo;   // ID = String (DUI)

    @Autowired
    private EmpleadoRepository empleadoRepo; // ID = String (DUI)

    // LISTAR
    public List<MensajeDTO> obtenerTodos() {
        return mensajeRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public MensajeDTO obtenerPorId(Long id) {
        MensajeEntities e = mensajeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el mensaje con ID: " + id));
        return toDTO(e);
    }

    // CREAR
    public MensajeDTO registrar(@Valid MensajeDTO dto) {
        ChatEntities chat = chatRepo.findById(dto.getIdChat())
                .orElseThrow(() -> new EntityNotFoundException("No existe Chat con ID: " + dto.getIdChat()));

        MensajeEntities e = new MensajeEntities();
        e.setIdMensaje(null); // asegurar inserción (si usas secuencia)
        e.setChat(chat);

        // Remitentes opcionales (uno u otro)
        if (dto.getDuiClienteRemitente() != null && !dto.getDuiClienteRemitente().isBlank()) {
            ClienteEntities cliente = clienteRepo.findById(dto.getDuiClienteRemitente())
                    .orElseThrow(() -> new EntityNotFoundException("No existe Cliente con DUI: " + dto.getDuiClienteRemitente()));
            e.setClienteRemitente(cliente);
        } else {
            e.setClienteRemitente(null);
        }

        if (dto.getDuiEmpleadoRemitente() != null && !dto.getDuiEmpleadoRemitente().isBlank()) {
            EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleadoRemitente())
                    .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleadoRemitente()));
            e.setEmpleadoRemitente(empleado);
        } else {
            e.setEmpleadoRemitente(null);
        }

        e.setContenido(dto.getContenido());
        // Si fechaHora es null, dejamos que DB aplique DEFAULT CURRENT_TIMESTAMP
        if (dto.getFechaHora() != null) e.setFechaHora(dto.getFechaHora());
        e.setLeido(dto.getLeido()); // 'S' / 'N' (puede venir null y DB tener default 'N')

        e = mensajeRepo.save(e);
        return toDTO(e);
    }

    // ACTUALIZAR
    public MensajeDTO actualizar(Long id, @Valid MensajeDTO dto) {
        MensajeEntities e = mensajeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el mensaje con ID: " + id));

        ChatEntities chat = chatRepo.findById(dto.getIdChat())
                .orElseThrow(() -> new EntityNotFoundException("No existe Chat con ID: " + dto.getIdChat()));
        e.setChat(chat);

        // Remitentes opcionales
        if (dto.getDuiClienteRemitente() != null && !dto.getDuiClienteRemitente().isBlank()) {
            ClienteEntities cliente = clienteRepo.findById(dto.getDuiClienteRemitente())
                    .orElseThrow(() -> new EntityNotFoundException("No existe Cliente con DUI: " + dto.getDuiClienteRemitente()));
            e.setClienteRemitente(cliente);
        } else {
            e.setClienteRemitente(null);
        }

        if (dto.getDuiEmpleadoRemitente() != null && !dto.getDuiEmpleadoRemitente().isBlank()) {
            EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleadoRemitente())
                    .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleadoRemitente()));
            e.setEmpleadoRemitente(empleado);
        } else {
            e.setEmpleadoRemitente(null);
        }

        e.setContenido(dto.getContenido());
        if (dto.getFechaHora() != null) e.setFechaHora(dto.getFechaHora());
        e.setLeido(dto.getLeido());

        e = mensajeRepo.save(e);
        return toDTO(e);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        if (!mensajeRepo.existsById(id)) return false;
        mensajeRepo.deleteById(id);
        return true;
    }

    // ====== MAPPER ======
    private MensajeDTO toDTO(MensajeEntities e) {
        MensajeDTO d = new MensajeDTO();
        d.setIdMensaje(e.getIdMensaje());
        d.setContenido(e.getContenido());
        d.setFechaHora(e.getFechaHora());
        d.setLeido(e.getLeido());

        if (e.getChat() != null) d.setIdChat(e.getChat().getIdChat());
        if (e.getClienteRemitente() != null) d.setDuiClienteRemitente(e.getClienteRemitente().getDuiCliente());
        if (e.getEmpleadoRemitente() != null) d.setDuiEmpleadoRemitente(e.getEmpleadoRemitente().getDuiEmpleado());

        return d;
    }
}
