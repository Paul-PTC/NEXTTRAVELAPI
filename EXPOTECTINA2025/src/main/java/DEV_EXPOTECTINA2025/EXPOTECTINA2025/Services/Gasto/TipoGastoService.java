package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Gasto;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.TipoGastoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TipoGastoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.TipoGastoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TipoGastoService {

    @Autowired
    private TipoGastoRepository tipoGastoRepo;

    // LISTAR
    public List<TipoGastoDTO> obtenerTodos() {
        return tipoGastoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public TipoGastoDTO obtenerPorId(Long id) {
        TipoGastoEntities e = tipoGastoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tipo de gasto con ID: " + id));
        return toDTO(e);
    }

    // CREAR
    public TipoGastoDTO registrar(@Valid TipoGastoDTO dto) {
        TipoGastoEntities e = new TipoGastoEntities();
        e.setIdTipoGasto(null); // asegurar inserción (si usas secuencia)
        e.setNombreTipo(dto.getNombreTipo());
        e.setDescripcion(dto.getDescripcion());
        e = tipoGastoRepo.save(e);
        return toDTO(e);
    }

    // ACTUALIZAR
    public TipoGastoDTO actualizar(Long id, @Valid TipoGastoDTO dto) {
        TipoGastoEntities e = tipoGastoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el tipo de gasto con ID: " + id));

        e.setNombreTipo(dto.getNombreTipo());
        e.setDescripcion(dto.getDescripcion());

        e = tipoGastoRepo.save(e);
        return toDTO(e);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        if (!tipoGastoRepo.existsById(id)) return false;
        tipoGastoRepo.deleteById(id);
        return true;
    }

    // ===== MAPPER =====
    private TipoGastoDTO toDTO(TipoGastoEntities e) {
        TipoGastoDTO d = new TipoGastoDTO();
        d.setIdTipoGasto(e.getIdTipoGasto());
        d.setNombreTipo(e.getNombreTipo());
        d.setDescripcion(e.getDescripcion());
        return d;
    }
}