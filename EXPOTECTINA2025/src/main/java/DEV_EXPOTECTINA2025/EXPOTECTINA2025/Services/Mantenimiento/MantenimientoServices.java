package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Mantenimiento;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.GastoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.MantenimientoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.VehiculosEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.MantenimientoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.GastoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.MantenimientoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.VehiculosRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MantenimientoServices {

    private final MantenimientoRepository mantenimientoRepo;
    private final VehiculosRepository vehiculoRepo;
    private final GastoRepository gastoRepo;

    // listar
    public List<MantenimientoDTO> listar() {
        return mantenimientoRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // obtener por id
    public Optional<MantenimientoDTO> obtenerPorId(Long id) {
        return mantenimientoRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public MantenimientoDTO crear(MantenimientoDTO dto) {
        MantenimientoEntities e = new MantenimientoEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(mantenimientoRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<MantenimientoDTO> actualizar(Long id, MantenimientoDTO dto) {
        Optional<MantenimientoEntities> opt = mantenimientoRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        MantenimientoEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(mantenimientoRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!mantenimientoRepo.existsById(id)) return false;
        mantenimientoRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private MantenimientoDTO toDTO(MantenimientoEntities e) {
        MantenimientoDTO d = new MantenimientoDTO();
        d.setIdMantenimiento(e.getIdMantenimiento());
        d.setIdVehiculo(e.getVehiculo() != null ? e.getVehiculo().getIdVehiculo() : null);
        d.setIdGasto(e.getGasto() != null ? e.getGasto().getIdGasto() : null);
        d.setFechaMantenimiento(e.getFechaMantenimiento());
        d.setRealizador(e.getRealizador());
        d.setDescripcion(e.getDescripcion());
        return d;
    }

    // aplicar DTO a Entity
    private void applyDtoToEntity(MantenimientoDTO d, MantenimientoEntities e, boolean isUpdate) {
        if (d.getIdVehiculo() != null || !isUpdate) {
            if (d.getIdVehiculo() != null) {
                VehiculosEntities v = vehiculoRepo.findById(d.getIdVehiculo())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Vehículo con ID: " + d.getIdVehiculo()));
                e.setVehiculo(v);
            } else {
                e.setVehiculo(null);
            }
        }
        if (d.getIdGasto() != null || !isUpdate) {
            if (d.getIdGasto() != null) {
                GastoEntities g = gastoRepo.findById(d.getIdGasto())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Gasto con ID: " + d.getIdGasto()));
                e.setGasto(g);
            } else {
                e.setGasto(null);
            }
        }
        e.setFechaMantenimiento(d.getFechaMantenimiento());
        e.setRealizador(d.getRealizador());
        e.setDescripcion(d.getDescripcion());
    }
}
