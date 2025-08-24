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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionLugarService {

    @Autowired
    private CalificacionLugarRepository repo;

    @Autowired
    private UserRepository repoUsuario;

    @Autowired
    private LugarTuristicoRepository repoLugar;

    @Autowired
    private EntityManager entityManager;

    public CalificacionLugarDTO registrar(CalificacionLugarDTO dto) {
        CalificacionLugarEntity entity = convertirAEntity(dto);
        CalificacionLugarEntity guardado = repo.save(entity);

        return convertirADTO(guardado);
    }

    public boolean eliminar(Long id) {
        CalificacionLugarEntity existente = repo.findById(id).orElse(null);
        if (existente != null) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CalificacionLugarDTO> obtenerTodos() {
        return repo.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private CalificacionLugarDTO convertirADTO(CalificacionLugarEntity e) {
        CalificacionLugarDTO dto = new CalificacionLugarDTO();
        dto.setIdCalificacionLugar(e.getIdCalificacionLugar());
        dto.setIdLugar(e.getLugar().getIdLugar());
        dto.setIdUsuario(e.getUsuario().getId());
        dto.setCalificacion(e.getCalificacion());
        dto.setComentario(e.getComentario());
        dto.setFechaCalificacion(e.getFechaCalificacion());
        return dto;
    }


    public CalificacionLugarEntity convertirAEntity(CalificacionLugarDTO dto) {
        CalificacionLugarEntity entity = new CalificacionLugarEntity();
        // otras asignaciones simples...

        LugarTuristicoEntity lugar = entityManager.getReference(LugarTuristicoEntity.class, dto.getIdLugar());
        UserEntity usuario = entityManager.getReference(UserEntity.class, dto.getIdUsuario());

        entity.setLugar(lugar);
        entity.setUsuario(usuario);

        entity.setCalificacion(dto.getCalificacion());
        entity.setComentario(dto.getComentario());
        entity.setFechaCalificacion(dto.getFechaCalificacion());

        return entity;
    }
    public CalificacionLugarDTO actualizarCalificacion(Long id, CalificacionLugarDTO dto) {
        CalificacionLugarEntity existente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Calificación no encontrada con ID: " + id));

        // Actualiza campos simples
        existente.setCalificacion(dto.getCalificacion());
        existente.setComentario(dto.getComentario());
        existente.setFechaCalificacion(dto.getFechaCalificacion());

        // Actualiza relaciones (usuario y lugar)
        UserEntity usuario = repoUsuario.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + dto.getIdUsuario()));
        existente.setUsuario(usuario);

        LugarTuristicoEntity lugar = repoLugar.findById(dto.getIdLugar())
                .orElseThrow(() -> new EntityNotFoundException("Lugar no encontrado con ID: " + dto.getIdLugar()));
        existente.setLugar(lugar);

        CalificacionLugarEntity actualizado = repo.save(existente);
        return convertirADTO(actualizado);
    }

}

