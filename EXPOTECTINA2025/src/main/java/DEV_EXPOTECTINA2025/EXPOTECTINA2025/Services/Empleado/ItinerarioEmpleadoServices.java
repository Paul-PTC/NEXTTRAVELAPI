package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ItinerarioEmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.VehiculosEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ItinerarioEmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.VehiculosRepository;
import jakarta.persistence.EntityNotFoundException;
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
public class ItinerarioEmpleadoServices {

    private final ItinerarioEmpleadoRepository itinRepo;
    private final EmpleadoRepository empleadoRepo;
    private final VehiculosRepository vehiculoRepo;
    private final RutaRepository rutaRepo;

    // listar
    public List<ItinerarioEmpleadoDTO> listar() {
        return itinRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<ItinerarioEmpleadoDTO> obtenerPorId(Long id) {
        return itinRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public ItinerarioEmpleadoDTO crear(ItinerarioEmpleadoDTO dto) {
        ItinerarioEmpleadoEntities e = new ItinerarioEmpleadoEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(itinRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<ItinerarioEmpleadoDTO> actualizar(Long id, ItinerarioEmpleadoDTO dto) {
        Optional<ItinerarioEmpleadoEntities> opt = itinRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        ItinerarioEmpleadoEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(itinRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!itinRepo.existsById(id)) return false;
        itinRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private ItinerarioEmpleadoDTO toDTO(ItinerarioEmpleadoEntities e) {
        ItinerarioEmpleadoDTO d = new ItinerarioEmpleadoDTO();
        d.setIdItinerario(e.getIdItinerario());
        d.setDuiEmpleado(e.getEmpleado() != null ? e.getEmpleado().getDuiEmpleado() : null);
        d.setIdVehiculo(e.getVehiculo() != null ? e.getVehiculo().getIdVehiculo() : null);
        d.setIdRuta(e.getRuta() != null ? e.getRuta().getIdRuta() : null);
        d.setFecha(e.getFecha());
        d.setHoraInicio(e.getHoraInicio());
        d.setHoraFin(e.getHoraFin());
        d.setObservaciones(e.getObservaciones());
        return d;
    }

    // aplicar DTO a Entity
    private void applyDtoToEntity(ItinerarioEmpleadoDTO d, ItinerarioEmpleadoEntities e, boolean isUpdate) {
        if (d.getDuiEmpleado() != null || !isUpdate) {
            if (d.getDuiEmpleado() != null) {
                EmpleadoEntities emp = empleadoRepo.findById(d.getDuiEmpleado())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + d.getDuiEmpleado()));
                e.setEmpleado(emp);
            } else {
                e.setEmpleado(null);
            }
        }

        if (d.getIdVehiculo() != null || !isUpdate) {
            if (d.getIdVehiculo() != null) {
                VehiculosEntities veh = vehiculoRepo.findById(d.getIdVehiculo())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Vehículo con ID: " + d.getIdVehiculo()));
                e.setVehiculo(veh);
            } else {
                e.setVehiculo(null);
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

        e.setFecha(d.getFecha());
        e.setHoraInicio(d.getHoraInicio());
        e.setHoraFin(d.getHoraFin());
        e.setObservaciones(d.getObservaciones());
    }
}
