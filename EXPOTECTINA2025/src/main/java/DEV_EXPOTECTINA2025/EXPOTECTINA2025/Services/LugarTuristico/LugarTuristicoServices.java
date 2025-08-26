package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.LugarTuristicoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RangoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.LugarTuristicoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.LugarTuristicoRepository;
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
public class LugarTuristicoServices {

    private final LugarTuristicoRepository lugarRepo;

    // listar
    public List<LugarTuristicoDTO> listar() {
        return lugarRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // obtener por id
    public Optional<LugarTuristicoDTO> obtenerPorId(Long id) {
        return lugarRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public LugarTuristicoDTO crear(LugarTuristicoDTO dto) {
        LugarTuristicoEntity e = new LugarTuristicoEntity();
        applyDtoToEntity(dto, e);
        return toDTO(lugarRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<LugarTuristicoDTO> actualizar(Long id, LugarTuristicoDTO dto) {
        Optional<LugarTuristicoEntity> opt = lugarRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        LugarTuristicoEntity e = opt.get();
        applyDtoToEntity(dto, e);
        return Optional.of(toDTO(lugarRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!lugarRepo.existsById(id)) return false;
        lugarRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private LugarTuristicoDTO toDTO(LugarTuristicoEntity e) {
        LugarTuristicoDTO d = new LugarTuristicoDTO();
        d.setIdLugar(e.getIdLugar());
        d.setNombre(e.getNombre());
        d.setTipoLugar(e.getTipoLugar());
        d.setDescripcion(e.getDescripcion());
        d.setLatitud(e.getLatitud());
        d.setLongitud(e.getLongitud());
        return d;
    }

    // aplicar DTO a Entity
    private void applyDtoToEntity(LugarTuristicoDTO d, LugarTuristicoEntity e) {
        e.setNombre(d.getNombre());
        e.setTipoLugar(d.getTipoLugar());
        e.setDescripcion(d.getDescripcion());
        e.setLatitud(d.getLatitud());
        e.setLongitud(d.getLongitud());
    }
}
