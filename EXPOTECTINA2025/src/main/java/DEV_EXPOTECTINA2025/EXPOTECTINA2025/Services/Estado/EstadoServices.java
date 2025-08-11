package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Estado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EstadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EstadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoServices {
    @Autowired
    private EstadoRepository estadoRepository;

    // Convertir Entity a DTO
    private EstadoDTO convertirAEstadoDTO(EstadoEntities entity) {
        EstadoDTO dto = new EstadoDTO();
        dto.setIdEstado(entity.getIdEstado());
        dto.setNombreEstado(entity.getNombreEstado());
        return dto;
    }

    // Convertir DTO a Entity
    private EstadoEntities convertirAEstadoEntities(EstadoDTO dto) {
        EstadoEntities entity = new EstadoEntities();
        entity.setIdEstado(dto.getIdEstado());
        entity.setNombreEstado(dto.getNombreEstado());
        return entity;
    }

    public List<EstadoDTO> obtenerTodosEstados() {
        return estadoRepository.findAll()
                .stream()
                .map(this::convertirAEstadoDTO)
                .collect(Collectors.toList());
    }

    public EstadoDTO obtenerEstadoPorId(Integer id) {
        return estadoRepository.findById(id)
                .map(this::convertirAEstadoDTO)
                .orElse(null);
    }

    public EstadoDTO insertarEstado(EstadoDTO dto) {
        EstadoEntities entity = convertirAEstadoEntities(dto);
        EstadoEntities guardado = estadoRepository.save(entity);
        return convertirAEstadoDTO(guardado);
    }

    public EstadoDTO actualizarEstado(Integer id, EstadoDTO dto) {
        EstadoEntities existente = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        existente.setNombreEstado(dto.getNombreEstado());
        EstadoEntities actualizado = estadoRepository.save(existente);
        return convertirAEstadoDTO(actualizado);
    }

    public void eliminarEstado(Integer id) {
        estadoRepository.deleteById(id);
    }
}
