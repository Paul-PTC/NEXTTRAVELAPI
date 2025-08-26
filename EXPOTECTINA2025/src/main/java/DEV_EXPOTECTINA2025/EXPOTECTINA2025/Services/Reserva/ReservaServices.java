package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Reserva;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ReservaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import jakarta.persistence.EntityNotFoundException;
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
public class ReservaServices {

    private final ReservaRepository reservaRepo;
    private final EmpleadoRepository empleadoRepo;
    private final RutaRepository rutaRepo;

    // listar
    public List<ReservaDTO> listar() {
        return reservaRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<ReservaDTO> obtenerPorId(Long id) {
        return reservaRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public ReservaDTO crear(ReservaDTO dto) {
        ReservaEntities e = new ReservaEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(reservaRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<ReservaDTO> actualizar(Long id, ReservaDTO dto) {
        Optional<ReservaEntities> opt = reservaRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        ReservaEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(reservaRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!reservaRepo.existsById(id)) return false;
        reservaRepo.deleteById(id);
        return true;
    }

    // mapper entity->dto
    private ReservaDTO toDTO(ReservaEntities e) {
        ReservaDTO d = new ReservaDTO();
        d.setId(e.getId());
        d.setDuiEmpleado(e.getEmpleado() != null ? e.getEmpleado().getDuiEmpleado() : null);
        d.setIdRuta(e.getRuta() != null ? e.getRuta().getIdRuta() : null);
        d.setFechaReserva(e.getFechaReserva());
        d.setFechaViaje(e.getFechaViaje());
        d.setEstado(e.getEstado());
        d.setCantidadPasajeros(e.getCantidadPasajeros());
        d.setDescripcion(e.getDescripcion());
        return d;
    }

    // aplicar dto->entity
    private void applyDtoToEntity(ReservaDTO d, ReservaEntities e, boolean isUpdate) {
        if (d.getDuiEmpleado() != null || !isUpdate) {
            if (d.getDuiEmpleado() != null) {
                EmpleadoEntities emp = empleadoRepo.findById(d.getDuiEmpleado())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + d.getDuiEmpleado()));
                e.setEmpleado(emp);
            } else {
                e.setEmpleado(null);
            }
        }
        if (d.getIdRuta() != null || !isUpdate) {
            if (d.getIdRuta() != null) {
                RutaEntities ruta = rutaRepo.findById(d.getIdRuta())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Ruta con ID: " + d.getIdRuta()));
                e.setRuta(ruta);
            } else {
                e.setRuta(null);
            }
        }
        e.setFechaReserva(d.getFechaReserva());
        e.setFechaViaje(d.getFechaViaje());
        e.setEstado(d.getEstado());
        e.setCantidadPasajeros(d.getCantidadPasajeros());
        e.setDescripcion(d.getDescripcion());
    }
}