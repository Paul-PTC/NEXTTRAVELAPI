package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Gasto;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.GastoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.TipoGastoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.GastoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.GastoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.TipoGastoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepo;

    @Autowired
    private TipoGastoRepository tipoGastoRepo;

    @Autowired
    private EmpleadoRepository empleadoRepo;

    // LISTAR
    public List<GastoDTO> obtenerTodos() {
        return gastoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public GastoDTO obtenerPorId(Long id) {
        GastoEntities e = gastoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el gasto con ID: " + id));
        return toDTO(e);
    }

    // CREAR
    public GastoDTO registrar(@Valid GastoDTO dto) {
        validar(dto);

        TipoGastoEntities tipo = tipoGastoRepo.findById(dto.getIdTipoGasto())
                .orElseThrow(() -> new EntityNotFoundException("No existe TipoGasto con ID: " + dto.getIdTipoGasto()));

        EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleado()));

        GastoEntities e = new GastoEntities();
        e.setIdGasto(null); // asegurar inserción
        e.setTipoGasto(tipo);
        e.setEmpleado(empleado);
        e.setMonto(dto.getMonto());
        e.setFechaGasto(dto.getFechaGasto());
        e.setDescripcion(dto.getDescripcion());

        e = gastoRepo.save(e);
        return toDTO(e);
    }

    // ACTUALIZAR
    public GastoDTO actualizar(Long id, @Valid GastoDTO dto) {
        validar(dto);

        GastoEntities e = gastoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el gasto con ID: " + id));

        TipoGastoEntities tipo = tipoGastoRepo.findById(dto.getIdTipoGasto())
                .orElseThrow(() -> new EntityNotFoundException("No existe TipoGasto con ID: " + dto.getIdTipoGasto()));

        EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleado()));

        e.setTipoGasto(tipo);
        e.setEmpleado(empleado);
        e.setMonto(dto.getMonto());
        e.setFechaGasto(dto.getFechaGasto());
        e.setDescripcion(dto.getDescripcion());

        e = gastoRepo.save(e);
        return toDTO(e);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        if (!gastoRepo.existsById(id)) return false; // ← corregido
        gastoRepo.deleteById(id);
        return true;
    }

    // ====== MAPPERS ======
    private GastoDTO toDTO(GastoEntities e) {
        GastoDTO d = new GastoDTO();
        d.setIdGasto(e.getIdGasto());
        d.setMonto(e.getMonto());
        d.setFechaGasto(e.getFechaGasto());
        d.setDescripcion(e.getDescripcion());
        if (e.getTipoGasto() != null) d.setIdTipoGasto(e.getTipoGasto().getIdTipoGasto());
        if (e.getEmpleado() != null) d.setDuiEmpleado(e.getEmpleado().getDuiEmpleado());
        return d;
    }

    // ====== VALIDACIONES BÁSICAS ======
    private void validar(GastoDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El cuerpo de la solicitud no puede ser nulo");
        if (dto.getIdTipoGasto() == null) throw new IllegalArgumentException("El tipo de gasto es obligatorio");
        if (dto.getDuiEmpleado() == null || dto.getDuiEmpleado().isBlank())
            throw new IllegalArgumentException("El DUI del empleado es obligatorio");
        if (dto.getMonto() == null) throw new IllegalArgumentException("El monto es obligatorio");
        if (dto.getFechaGasto() == null) throw new IllegalArgumentException("La fecha del gasto es obligatoria");
    }
}
