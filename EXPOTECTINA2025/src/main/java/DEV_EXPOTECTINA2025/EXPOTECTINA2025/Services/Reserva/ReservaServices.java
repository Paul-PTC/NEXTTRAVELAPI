package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Reserva;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ReservaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaServices {
    @Autowired
    private ReservaRepository reservaRepository;

    // Listar todos (devuelve DTOs)
    public List<ReservaDTO> obtenerTodasLasReservas() {
        List<ReservaEntities> entities = reservaRepository.findAll();
        return entities.stream()
                .map(this::convertirAReservaDTO)
                .collect(Collectors.toList());
    }

    // Obtener por Id
    public Optional<ReservaDTO> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id)
                .map(this::convertirAReservaDTO);
    }

    // Insertar
    public ReservaDTO insertarReserva(ReservaEntities dto) {
        ReservaEntities entity = convertirAReservaEntity(dto);
        ReservaEntities guardada = reservaRepository.save(entity);
        return convertirAReservaDTO(guardada);
    }

    // Actualizar
    public ReservaDTO actualizarReserva(Long id, ReservaEntities dto) {
        ReservaEntities entity = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));

        entity.setDuiEmpleado(dto.getDuiEmpleado());
        entity.setIdRuta(dto.getIdRuta());
        entity.setFechaReserva(dto.getFechaReserva());
        entity.setFechaViaje(dto.getFechaViaje());
        entity.setEstado(dto.getEstado());
        entity.setCantidadPasajeros(dto.getCantidadPasajeros());
        entity.setDescripcion(dto.getDescripcion());

        ReservaEntities actualizada = reservaRepository.save(entity);
        return convertirAReservaDTO(actualizada);
    }

    // Eliminar
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    // Convertir Entity a DTO
    private ReservaDTO convertirAReservaDTO(ReservaEntities entity) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(Long.valueOf(entity.getId()));
        dto.setDuiEmpleado(entity.getDuiEmpleado());
        dto.setIdRuta(entity.getIdRuta());
        dto.setFechaReserva(entity.getFechaReserva());
        dto.setFechaViaje(entity.getFechaViaje());
        dto.setEstado(entity.getEstado());
        dto.setCantidadPasajeros(entity.getCantidadPasajeros());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }

    // Convertir DTO a Entity
    private ReservaEntities convertirAReservaEntity(ReservaEntities dto) {
        ReservaEntities entity = new ReservaEntities();
        entity.setId(dto.getId());
        entity.setDuiEmpleado(dto.getDuiEmpleado());
        entity.setIdRuta(dto.getIdRuta());
        entity.setFechaReserva(dto.getFechaReserva());
        entity.setFechaViaje(dto.getFechaViaje());
        entity.setEstado(dto.getEstado());
        entity.setCantidadPasajeros(dto.getCantidadPasajeros());
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }
}