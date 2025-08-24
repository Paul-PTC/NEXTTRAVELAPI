package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.MultimediaPaqueteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.MultimediaPaqueteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.MultimediaPaqueteRepository;
import jakarta.persistence.EntityManager;
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

    private MultimediaPaqueteRepository multimediaRepo;

    @PersistenceContext
    private EntityManager em;

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
        MultimediaPaqueteEntities saved = multimediaRepo.save(e);
        return toDTO(saved);
    }

    // actualizar
    @Transactional
    public Optional<MultimediaPaqueteDTO> actualizar(Long id, MultimediaPaqueteDTO dto) {
        Optional<MultimediaPaqueteEntities> opt = multimediaRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        MultimediaPaqueteEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        MultimediaPaqueteEntities saved = multimediaRepo.save(e);
        return Optional.of(toDTO(saved));
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
        MultimediaPaqueteDTO dto = new MultimediaPaqueteDTO();
        dto.setIdMultimedia(e.getIdMultimedia());
        dto.setIdLugar(e.getLugar() != null ? e.getLugar().getIdLugar() : null);
        dto.setUrl(e.getUrl());
        dto.setTipo(e.getTipo());
        dto.setDescripcion(e.getDescripcion());
        return dto;
    }

    // aplicar datos del DTO a la Entity
    private void applyDtoToEntity(MultimediaPaqueteDTO dto, MultimediaPaqueteEntities e, boolean isUpdate) {
        if (dto.getIdLugar() != null || !isUpdate) {
            e.setLugar(dto.getIdLugar() != null
                    ? em.getReference(LugarTuristicoEntity.class, dto.getIdLugar())
                    : null);
        }
        e.setUrl(dto.getUrl());
        e.setTipo(dto.getTipo());
        e.setDescripcion(dto.getDescripcion());
    }
}
