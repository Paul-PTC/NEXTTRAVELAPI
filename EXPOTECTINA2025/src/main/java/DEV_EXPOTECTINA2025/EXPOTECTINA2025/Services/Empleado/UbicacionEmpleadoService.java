package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UbicacionEmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.UbicacionEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UbicacionEmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
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
public class UbicacionEmpleadoService {

    private UbicacionEmpleadoRepository ubicacionRepo;
    private EmpleadoRepository empleadoRepo;

    // listar
    public List<UbicacionEmpleadoDTO> listar() {
        return ubicacionRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<UbicacionEmpleadoDTO> obtenerPorId(Long id) {
        return ubicacionRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public UbicacionEmpleadoDTO crear(UbicacionEmpleadoDTO dto) {
        UbicacionEmpleadoEntities e = new UbicacionEmpleadoEntities();
        applyDtoToEntity(dto, e, false);
        UbicacionEmpleadoEntities saved = ubicacionRepo.save(e);
        return toDTO(saved);
    }

    // actualizar
    @Transactional
    public Optional<UbicacionEmpleadoDTO> actualizar(Long id, UbicacionEmpleadoDTO dto) {
        Optional<UbicacionEmpleadoEntities> opt = ubicacionRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        UbicacionEmpleadoEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        UbicacionEmpleadoEntities saved = ubicacionRepo.save(e);
        return Optional.of(toDTO(saved));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!ubicacionRepo.existsById(id)) return false;
        ubicacionRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private UbicacionEmpleadoDTO toDTO(UbicacionEmpleadoEntities e) {
        UbicacionEmpleadoDTO dto = new UbicacionEmpleadoDTO();
        dto.setIdUbicacionEmpleado(e.getIdUbicacionEmpleado());
        dto.setDuiEmpleado(e.getEmpleado() != null ? e.getEmpleado().getDuiEmpleado() : null);
        dto.setLatitud(e.getLatitud());
        dto.setLongitud(e.getLongitud());
        dto.setFechaHoraRegistroE(e.getFechaHoraRegistroE());
        return dto;
    }

    // aplicar datos del DTO a la Entity
    private void applyDtoToEntity(UbicacionEmpleadoDTO dto, UbicacionEmpleadoEntities e, boolean isUpdate) {
        if (dto.getDuiEmpleado() != null || !isUpdate) {
            if (dto.getDuiEmpleado() != null) {
                EmpleadoEntities emp = empleadoRepo.findById(dto.getDuiEmpleado())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No existe Empleado con DUI: " + dto.getDuiEmpleado()));
                e.setEmpleado(emp);
            } else {
                e.setEmpleado(null);
            }
        }
        e.setLatitud(dto.getLatitud());
        e.setLongitud(dto.getLongitud());
        e.setFechaHoraRegistroE(dto.getFechaHoraRegistroE());
    }
}
