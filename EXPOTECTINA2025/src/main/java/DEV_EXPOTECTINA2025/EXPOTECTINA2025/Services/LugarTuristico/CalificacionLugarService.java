package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.CalificacionLugarEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.LugarTuristicoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UserEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.CalificacionLugarDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.CalificacionLugarRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.LugarTuristicoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalificacionLugarService {

    private final CalificacionLugarRepository calificacionRepo;
    private final LugarTuristicoRepository lugarRepo;
    private final UserRepository usuarioRepo;

    // listar
    public List<CalificacionLugarDTO> listar() {
        return calificacionRepo.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // obtener por id
    public Optional<CalificacionLugarDTO> obtenerPorId(Long id) {
        return calificacionRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public CalificacionLugarDTO crear(CalificacionLugarDTO dto) {
        CalificacionLugarEntity e = new CalificacionLugarEntity();
        applyDtoToEntity(dto, e, false);
        return toDTO(calificacionRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<CalificacionLugarDTO> actualizar(Long id, CalificacionLugarDTO dto) {
        Optional<CalificacionLugarEntity> opt = calificacionRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        CalificacionLugarEntity e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(calificacionRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!calificacionRepo.existsById(id)) return false;
        calificacionRepo.deleteById(id);
        return true;
    }

    // mapper entity->dto
    private CalificacionLugarDTO toDTO(CalificacionLugarEntity e) {
        CalificacionLugarDTO d = new CalificacionLugarDTO();
        d.setIdCalificacionLugar(e.getIdCalificacionLugar());
        d.setIdLugar(e.getLugar() != null ? e.getLugar().getIdLugar() : null);
        d.setIdUsuario(e.getUsuario() != null ? e.getUsuario().getId() : null);
        d.setCalificacion(e.getCalificacion());
        d.setComentario(e.getComentario());
        d.setFechaCalificacion(e.getFechaCalificacion());
        return d;
    }

    // aplicar dto->entity
    private void applyDtoToEntity(CalificacionLugarDTO d, CalificacionLugarEntity e, boolean isUpdate) {
        if (d.getIdLugar() != null || !isUpdate) {
            if (d.getIdLugar() != null) {
                LugarTuristicoEntity lugar = lugarRepo.findById(d.getIdLugar())
                        .orElseThrow(() -> new EntityNotFoundException("No existe LugarTuristico con ID: " + d.getIdLugar()));
                e.setLugar(lugar);
            } else {
                e.setLugar(null);
            }
        }
        if (d.getIdUsuario() != null || !isUpdate) {
            if (d.getIdUsuario() != null) {
                UserEntity usuario = usuarioRepo.findById(d.getIdUsuario())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Usuario con ID: " + d.getIdUsuario()));
                e.setUsuario(usuario);
            } else {
                e.setUsuario(null);
            }
        }
        e.setCalificacion(d.getCalificacion());
        e.setComentario(d.getComentario());
        e.setFechaCalificacion(d.getFechaCalificacion());
    }
}

