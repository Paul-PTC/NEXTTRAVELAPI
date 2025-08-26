package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.LugarTuristicoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.MultimediaPaqueteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.MultimediaPaqueteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.LugarTuristicoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.MultimediaPaqueteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultimediaPaqueteService {

    private final MultimediaPaqueteRepository multimediaRepo;
    private final LugarTuristicoRepository lugarRepo;

    // listar
    public List<MultimediaPaqueteDTO> listar() {
        return multimediaRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<MultimediaPaqueteDTO> obtenerPorId(Long id) {
        return multimediaRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public MultimediaPaqueteDTO crear(MultimediaPaqueteDTO dto) {
        MultimediaPaqueteEntities e = new MultimediaPaqueteEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(multimediaRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<MultimediaPaqueteDTO> actualizar(Long id, MultimediaPaqueteDTO dto) {
        Optional<MultimediaPaqueteEntities> opt = multimediaRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        MultimediaPaqueteEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(multimediaRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!multimediaRepo.existsById(id)) return false;
        multimediaRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private MultimediaPaqueteDTO toDTO(MultimediaPaqueteEntities e) {
        MultimediaPaqueteDTO d = new MultimediaPaqueteDTO();
        d.setIdMultimedia(e.getIdMultimedia());
        d.setIdLugar(e.getLugar() != null ? e.getLugar().getIdLugar() : null);
        d.setUrl(e.getUrl());
        d.setTipo(e.getTipo());
        d.setDescripcion(e.getDescripcion());
        return d;
    }

    // aplicar DTO a Entity
    private void applyDtoToEntity(MultimediaPaqueteDTO d, MultimediaPaqueteEntities e, boolean isUpdate) {
        if (d.getIdLugar() != null || !isUpdate) {
            if (d.getIdLugar() != null) {
                LugarTuristicoEntity lugar = lugarRepo.findById(d.getIdLugar())
                        .orElseThrow(() -> new EntityNotFoundException("No existe LugarTuristico con ID: " + d.getIdLugar()));
                e.setLugar(lugar);
            } else {
                e.setLugar(null);
            }
        }
        e.setUrl(d.getUrl());
        e.setTipo(d.getTipo());
        e.setDescripcion(d.getDescripcion());
    }
}
