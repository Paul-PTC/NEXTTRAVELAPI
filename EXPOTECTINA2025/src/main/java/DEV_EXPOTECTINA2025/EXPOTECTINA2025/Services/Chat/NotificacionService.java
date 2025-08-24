package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.MensajeEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.NotificacionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.NotificacionDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.NotificacionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @PersistenceContext
    private EntityManager em;

    // LISTAR
    public List<NotificacionDTO> listar() {
        return notificacionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public Optional<NotificacionDTO> obtenerPorId(Long id) {
        return notificacionRepository.findById(id).map(this::toDTO);
    }

    // CREAR
    @Transactional
    public NotificacionDTO crear(NotificacionDTO dto) {
        NotificacionEntities e = new NotificacionEntities();

        // Defaults si no vienen en el DTO
        if (dto.getLeido() == null || dto.getLeido().isBlank()) dto.setLeido("N");
        if (dto.getFechaHora() == null) dto.setFechaHora(LocalDateTime.now());

        applyDtoToEntity(dto, e, false);
        NotificacionEntities saved = notificacionRepository.save(e);
        return toDTO(saved);
    }

    // ACTUALIZAR (PUT)
    @Transactional
    public Optional<NotificacionDTO> actualizar(Long id, NotificacionDTO dto) {
        Optional<NotificacionEntities> opt = notificacionRepository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        NotificacionEntities e = opt.get();

        // Mantener valores si no vienen
        if (dto.getLeido() == null || dto.getLeido().isBlank()) {
            dto.setLeido(e.getLeido() != null ? e.getLeido() : "N");
        }
        if (dto.getFechaHora() == null) {
            dto.setFechaHora(e.getFechaHora() != null ? e.getFechaHora() : LocalDateTime.now());
        }

        applyDtoToEntity(dto, e, true);
        NotificacionEntities saved = notificacionRepository.save(e);
        return Optional.of(toDTO(saved));
    }

    // ELIMINAR
    @Transactional
    public boolean eliminar(Long id) {
        if (!notificacionRepository.existsById(id)) return false;
        notificacionRepository.deleteById(id);
        return true;
    }

    // PATCH: marcar como leída
    @Transactional
    public Optional<NotificacionDTO> marcarComoLeida(Long id) {
        Optional<NotificacionEntities> opt = notificacionRepository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        NotificacionEntities e = opt.get();
        e.setLeido("S");
        NotificacionEntities saved = notificacionRepository.save(e);
        return Optional.of(toDTO(saved));
    }

    // Helpers de mapeo
    private NotificacionDTO toDTO(NotificacionEntities e) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setIdNotificacion(e.getIdNotificacion());
        dto.setDuiCliente(e.getCliente() != null ? e.getCliente().getDuiCliente() : null);
        dto.setDuiEmpleado(e.getEmpleado() != null ? e.getEmpleado().getDuiEmpleado() : null);
        dto.setIdMensaje(e.getMensajeRelacionado() != null ? e.getMensajeRelacionado().getIdMensaje() : null);
        dto.setMensaje(e.getMensaje());
        dto.setLeido(e.getLeido());
        dto.setFechaHora(e.getFechaHora());
        dto.setTipo(e.getTipo());
        return dto;
    }

    /**
     * Mapea el DTO a la Entity.
     * @param isUpdate true = solo cambia relaciones si el DTO trae valor (permite PUT sin borrar relaciones por null).
     */
    private void applyDtoToEntity(NotificacionDTO dto, NotificacionEntities e, boolean isUpdate) {
        // Relaciones ManyToOne con referencias (no hace SELECT completo)
        if (dto.getDuiCliente() != null || !isUpdate) {
            e.setCliente(dto.getDuiCliente() != null
                    ? em.getReference(ClienteEntities.class, dto.getDuiCliente())
                    : null);
        }
        if (dto.getDuiEmpleado() != null || !isUpdate) {
            e.setEmpleado(dto.getDuiEmpleado() != null
                    ? em.getReference(EmpleadoEntities.class, dto.getDuiEmpleado())
                    : null);
        }
        if (dto.getIdMensaje() != null || !isUpdate) {
            // Ajusta Long/Integer según tu MensajeEntities
            e.setMensajeRelacionado(dto.getIdMensaje() != null
                    ? em.getReference(MensajeEntities.class, dto.getIdMensaje())
                    : null);
        }

        e.setMensaje(dto.getMensaje());
        e.setLeido(dto.getLeido());
        e.setFechaHora(dto.getFechaHora());
        e.setTipo(dto.getTipo());
    }
}
