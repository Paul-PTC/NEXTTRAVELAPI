package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.EstadoViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EstadoViajeEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EstadoViajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EstadoViajeRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class EstadoViajeServices {
    @Autowired
    private EstadoViajeRepository estadoViajeRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    // Crear un nuevo Estado de Viaje
    public EstadoViajeDTO crear(EstadoViajeDTO dto) {
        EstadoViajeEntities entity = new EstadoViajeEntities();

        ReservaEntities reserva = reservaRepository.findById(dto.getIdReserva())
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + dto.getIdReserva()));

        entity.setReserva(reserva);
        entity.setEstado(dto.getEstado());
        entity.setFechaEstado(dto.getFechaEstado());
        entity.setObservacion(dto.getObservacion());

        EstadoViajeEntities guardado = estadoViajeRepository.save(entity);
        return convertirADTO(guardado);
    }

    // Listar todos los estados de viaje
    public List<EstadoViajeDTO> obtenerTodos() {
        return estadoViajeRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

     // Actualizar
    public EstadoViajeDTO actualizar(Long id, EstadoViajeDTO dto) {
        EstadoViajeEntities entity = estadoViajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estado de viaje no encontrado con ID: " + id));

        ReservaEntities reserva = reservaRepository.findById(dto.getIdReserva())
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + dto.getIdReserva()));

        entity.setReserva(reserva);
        entity.setEstado(dto.getEstado());
        entity.setFechaEstado(dto.getFechaEstado());
        entity.setObservacion(dto.getObservacion());

        EstadoViajeEntities actualizado = estadoViajeRepository.save(entity);
        return convertirADTO(actualizado);
    }

    // Eliminar EstadoViaje por ID
    public boolean eliminar(Long id) {
        if (!estadoViajeRepository.existsById(id)) {
            throw new EntityNotFoundException("Estado de viaje no encontrado con ID: " + id);
        }

        try {
            estadoViajeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Error al eliminar el estado de viaje con ID {}: {}", id, e.getMessage());
            return false;
        }
    }

    // Conversión de Entity a DTO
    private EstadoViajeDTO convertirADTO(EstadoViajeEntities entity) {
        EstadoViajeDTO dto = new EstadoViajeDTO();
        dto.setIdEstadoViaje(entity.getIdEstadoViaje());
        dto.setIdReserva(entity.getReserva().getId());
        dto.setEstado(entity.getEstado());
        dto.setFechaEstado(entity.getFechaEstado());
        dto.setObservacion(entity.getObservacion());
        return dto;
    }
}
