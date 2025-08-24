package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Ganancia;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.GananciaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.GananciaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.GananciaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GananciaService {

    @Autowired
    private GananciaRepository gananciaRepo;

    @Autowired
    private EmpleadoRepository empleadoRepo;

    @Autowired
    private ReservaRepository reservaRepo;

    // LISTAR
    public List<GananciaDTO> obtenerTodas() {
        return gananciaRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public GananciaDTO obtenerPorId(Long id) {
        GananciaEntities e = gananciaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la ganancia con ID: " + id));
        return toDTO(e);
    }

    // CREAR
    public GananciaDTO registrar(@Valid GananciaDTO dto) {
        EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleado()));

        ReservaEntities reserva = reservaRepo.findById(dto.getIdReserva())
                .orElseThrow(() -> new EntityNotFoundException("No existe Reserva con ID: " + dto.getIdReserva()));

        GananciaEntities e = new GananciaEntities();
        e.setIdGanancia(null); // asegurar inserción (si usas secuencia)
        e.setEmpleado(empleado);
        e.setReserva(reserva);
        e.setFechaGanancia(dto.getFechaGanancia());
        e.setDescripcion(dto.getDescripcion());
        e.setMonto(dto.getMonto());

        e = gananciaRepo.save(e);
        return toDTO(e);
    }

    // ACTUALIZAR
    public GananciaDTO actualizar(Long id, @Valid GananciaDTO dto) {
        GananciaEntities e = gananciaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la ganancia con ID: " + id));

        EmpleadoEntities empleado = empleadoRepo.findById(dto.getDuiEmpleado())
                .orElseThrow(() -> new EntityNotFoundException("No existe Empleado con DUI: " + dto.getDuiEmpleado()));

        ReservaEntities reserva = reservaRepo.findById(dto.getIdReserva())
                .orElseThrow(() -> new EntityNotFoundException("No existe Reserva con ID: " + dto.getIdReserva()));

        e.setEmpleado(empleado);
        e.setReserva(reserva);
        e.setFechaGanancia(dto.getFechaGanancia());
        e.setDescripcion(dto.getDescripcion());
        e.setMonto(dto.getMonto());

        e = gananciaRepo.save(e);
        return toDTO(e);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        if (!gananciaRepo.existsById(id)) return false;
        gananciaRepo.deleteById(id);
        return true;
    }

    // ====== MAPPER ======
    private GananciaDTO toDTO(GananciaEntities e) {
        GananciaDTO d = new GananciaDTO();
        d.setIdGanancia(e.getIdGanancia());
        d.setFechaGanancia(e.getFechaGanancia());
        d.setDescripcion(e.getDescripcion());
        d.setMonto(e.getMonto());
        if (e.getEmpleado() != null) d.setDuiEmpleado(e.getEmpleado().getDuiEmpleado());
        if (e.getReserva() != null) d.setIdReserva(e.getReserva().getId());
        return d;
    }
}
