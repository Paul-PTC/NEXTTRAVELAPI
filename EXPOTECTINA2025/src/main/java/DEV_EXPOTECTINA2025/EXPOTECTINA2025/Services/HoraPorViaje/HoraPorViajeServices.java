package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.HoraPorViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.*;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsHorasPorViajeNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.HorasPorViajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.HorasPorViajeRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.VehiculosRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;


import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HoraPorViajeServices {

    private final HorasPorViajeRepository horasRepo;
    private final EmpleadoRepository empleadoRepo;
    private final RutaRepository rutaRepo;

    // listar
    public List<HorasPorViajeDTO> listar() {
        return horasRepo.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // obtener por id
    public Optional<HorasPorViajeDTO> obtenerPorId(Long id) {
        return horasRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public HorasPorViajeDTO crear(HorasPorViajeDTO dto) {
        HorasPorViajeEntities e = new HorasPorViajeEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(horasRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<HorasPorViajeDTO> actualizar(Long id, HorasPorViajeDTO dto) {
        Optional<HorasPorViajeEntities> opt = horasRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        HorasPorViajeEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(horasRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!horasRepo.existsById(id)) return false;
        horasRepo.deleteById(id);
        return true;
    }

    // mapper entity -> dto
    private HorasPorViajeDTO toDTO(HorasPorViajeEntities e) {
        HorasPorViajeDTO d = new HorasPorViajeDTO();
        d.setIdHorasViaje(e.getIdHorasViaje());
        d.setDuiEmpleado(e.getEmpleado() != null ? e.getEmpleado().getDuiEmpleado() : null);
        d.setIdRuta(e.getRuta() != null ? e.getRuta().getIdRuta() : null);
        d.setFechaViaje(e.getFechaViaje());
        d.setHoraSalida(e.getHoraSalida());
        d.setHoraLlegada(e.getHoraLlegada());
        d.setDuracion(e.getDuracion());
        return d;
    }

    // aplicar dto -> entity
    private void applyDtoToEntity(HorasPorViajeDTO d, HorasPorViajeEntities e, boolean isUpdate) {
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

        e.setFechaViaje(d.getFechaViaje());
        e.setHoraSalida(d.getHoraSalida());
        e.setHoraLlegada(d.getHoraLlegada());
        e.setDuracion(d.getDuracion());
    }
}



