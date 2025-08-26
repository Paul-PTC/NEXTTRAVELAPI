package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Gasto;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.TipoGastoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TipoGastoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.TipoGastoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipoGastoService {

    private final TipoGastoRepository tipoGastoRepo;

    // listar
    public List<TipoGastoDTO> listar() {
        return tipoGastoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<TipoGastoDTO> obtenerPorId(Long id) {
        return tipoGastoRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public TipoGastoDTO crear(TipoGastoDTO dto) {
        TipoGastoEntities e = new TipoGastoEntities();
        applyDtoToEntity(dto, e);
        return toDTO(tipoGastoRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<TipoGastoDTO> actualizar(Long id, TipoGastoDTO dto) {
        Optional<TipoGastoEntities> opt = tipoGastoRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        TipoGastoEntities e = opt.get();
        applyDtoToEntity(dto, e);
        return Optional.of(toDTO(tipoGastoRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!tipoGastoRepo.existsById(id)) return false;
        tipoGastoRepo.deleteById(id);
        return true;
    }

    // mapper entity -> dto
    private TipoGastoDTO toDTO(TipoGastoEntities e) {
        TipoGastoDTO d = new TipoGastoDTO();
        d.setIdTipoGasto(e.getIdTipoGasto());
        d.setNombreTipo(e.getNombreTipo());
        d.setDescripcion(e.getDescripcion());
        return d;
    }

    // aplicar dto -> entity
    private void applyDtoToEntity(TipoGastoDTO d, TipoGastoEntities e) {
        e.setNombreTipo(d.getNombreTipo());
        e.setDescripcion(d.getDescripcion());
    }
}