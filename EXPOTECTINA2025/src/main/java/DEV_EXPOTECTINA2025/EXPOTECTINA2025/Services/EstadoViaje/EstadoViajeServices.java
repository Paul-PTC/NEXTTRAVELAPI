package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.EstadoViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EstadoViajeEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EstadoViajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EstadoViajeRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoViajeServices {
    @Autowired
    private EstadoViajeRepository estadoViajeRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    private EstadoViajeDTO toDTO(EstadoViajeEntities entity) {
        EstadoViajeDTO dto = new EstadoViajeDTO();
        dto.setId(entity.getId());
        dto.setIdReserva(entity.getReserva().getId());
        dto.setEstado(entity.getEstado());
        dto.setFechaEstado(entity.getFechaEstado());
        dto.setObservacion(entity.getObservacion());
        return dto;
    }

    private EstadoViajeEntities toEntity(EstadoViajeDTO dto) {
        EstadoViajeEntities entity = new EstadoViajeEntities();
        entity.setId(dto.getId());
        ReservaEntities reserva = reservaRepository.findById(dto.getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        entity.setReserva(reserva);
        entity.setEstado(dto.getEstado());
        entity.setFechaEstado(dto.getFechaEstado());
        entity.setObservacion(dto.getObservacion());
        return entity;
    }

    public List<EstadoViajeDTO> obtenerTodos() {
        return estadoViajeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EstadoViajeDTO obtenerPorId(Integer id) {
        return estadoViajeRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public EstadoViajeDTO insertar(EstadoViajeDTO dto) {
        EstadoViajeEntities entity = toEntity(dto);
        return toDTO(estadoViajeRepository.save(entity));
    }

    public EstadoViajeDTO actualizar(Integer id, EstadoViajeDTO dto) {
        EstadoViajeEntities entity = estadoViajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoViaje no encontrado"));
        ReservaEntities reserva = reservaRepository.findById(dto.getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        entity.setReserva(reserva);
        entity.setEstado(dto.getEstado());
        entity.setFechaEstado(dto.getFechaEstado());
        entity.setObservacion(dto.getObservacion());
        return toDTO(estadoViajeRepository.save(entity));
    }

    public void eliminar(Integer id) {
        estadoViajeRepository.deleteById(id);
    }
}
