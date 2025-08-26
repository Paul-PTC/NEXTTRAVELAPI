package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.CalificacionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.CalificacionDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.CalificacionRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
public class CalificacionServices {

    private final CalificacionRepository calificacionRepo;
    private final ReservaRepository reservaRepo;

    // listar
    public List<CalificacionDTO> listar() {
        return calificacionRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<CalificacionDTO> obtenerPorId(Long id) {
        return calificacionRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public CalificacionDTO crear(CalificacionDTO dto) {
        CalificacionEntities e = new CalificacionEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(calificacionRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<CalificacionDTO> actualizar(Long id, CalificacionDTO dto) {
        Optional<CalificacionEntities> opt = calificacionRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        CalificacionEntities e = opt.get();
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

    // mapper entity -> dto
    private CalificacionDTO toDTO(CalificacionEntities e) {
        CalificacionDTO d = new CalificacionDTO();
        d.setIdCalificacion(e.getIdCalificacion());
        d.setIdReserva(e.getReserva() != null ? e.getReserva().getId() : null);
        d.setCalificacion(e.getCalificacion());
        d.setFechaCalificacion(e.getFechaCalificacion());
        return d;
    }

    // aplicar dto -> entity
    private void applyDtoToEntity(CalificacionDTO d, CalificacionEntities e, boolean isUpdate) {
        if (d.getIdReserva() != null || !isUpdate) {
            if (d.getIdReserva() != null) {
                ReservaEntities r = reservaRepo.findById(d.getIdReserva())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Reserva con ID: " + d.getIdReserva()));
                e.setReserva(r);
            } else {
                e.setReserva(null);
            }
        }
        e.setCalificacion(d.getCalificacion());
        e.setFechaCalificacion(d.getFechaCalificacion());
    }
}