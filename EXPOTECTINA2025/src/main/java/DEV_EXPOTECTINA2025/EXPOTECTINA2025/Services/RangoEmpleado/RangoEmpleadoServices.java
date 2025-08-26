package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.RangoEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RangoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RangoRopository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RangoEmpleadoServices {

    private final RangoRopository rangoRepo;

    // listar
    public List<RangoDTO> listar() {
        return rangoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<RangoDTO> obtenerPorId(Long id) {
        return rangoRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public RangoDTO crear(RangoDTO dto) {
        RangoEntity e = new RangoEntity();
        applyDtoToEntity(dto, e);
        return toDTO(rangoRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<RangoDTO> actualizar(Long id, RangoDTO dto) {
        Optional<RangoEntity> opt = rangoRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        RangoEntity e = opt.get();
        applyDtoToEntity(dto, e);
        return Optional.of(toDTO(rangoRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!rangoRepo.existsById(id)) return false;
        rangoRepo.deleteById(id);
        return true;
    }

    // mapper entity -> dto
    private RangoDTO toDTO(RangoEntity e) {
        RangoDTO d = new RangoDTO();
        d.setIdRango(e.getIdRango());
        d.setNombreRango(e.getNombreRango());
        d.setDescripcion(e.getDescripcion());
        return d;
    }

    // aplicar dto -> entity
    private void applyDtoToEntity(RangoDTO d, RangoEntity e) {
        e.setNombreRango(d.getNombreRango());
        e.setDescripcion(d.getDescripcion());
    }
}
