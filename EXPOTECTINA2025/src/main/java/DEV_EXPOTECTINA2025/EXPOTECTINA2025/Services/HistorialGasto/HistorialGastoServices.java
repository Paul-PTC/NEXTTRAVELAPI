package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.HistorialGasto;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.*;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.HistorialGastoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.LugarTuristicoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class HistorialGastoServices {
    @Autowired
    private HistorialGastoRepository repo;
    @Autowired
    private EmpleadoRepository EmpleadoRepository;

    public List<HistorialGastoDTO> getAllHistorialGasto() {
        List<HistorialGastoEntity> HistorialGasto = repo.findAll();
        return HistorialGasto.stream()
                .map(this::convertirAHistorialGastoDTO)
                .collect(Collectors.toList());
    }

    public HistorialGastoDTO insertarHistorialGasto(HistorialGastoDTO dto) {
        try {
            HistorialGastoEntity nuevoGasto = new HistorialGastoEntity();

            // Asignamos los datos simples
            nuevoGasto.setFechaGasto(dto.getFechaGasto());
            nuevoGasto.setTotalGasto(dto.getTotalGasto());
            nuevoGasto.setObservaciones(dto.getObservaciones());

            // Relación con Empleado (por DUI)
            Optional<EmpleadoEntities> empleado = EmpleadoRepository.findById(dto.getDuiEmpleado());
            if (empleado.isPresent()) {
                nuevoGasto.setEmpleado(empleado.get());
            } else {
                return null; // Empleado no encontrado
            }

            // Guardamos
            repo.save(nuevoGasto);

            return dto;
        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error en consola
            return null;
        }
    }

    public HistorialGastoDTO actualizarHistorialGasto(Long idHistorialGasto, HistorialGastoDTO dto) {
        // 1. Verificar existencia del historial de gasto
        HistorialGastoEntity gastoExistente = repo.findById(idHistorialGasto)
                .orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Historial de gasto no encontrado"));

        // 2. Actualizar campos simples
        gastoExistente.setFechaGasto(dto.getFechaGasto());
        gastoExistente.setTotalGasto(dto.getTotalGasto());
        gastoExistente.setObservaciones(dto.getObservaciones());

        // 3. Actualizar relación con Empleado
        if (dto.getDuiEmpleado() != null && !dto.getDuiEmpleado().isBlank()) {
            EmpleadoEntities empleado = EmpleadoRepository.findById(dto.getDuiEmpleado())
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con DUI proporcionado"));
            gastoExistente.setEmpleado(empleado);
        } else {
            gastoExistente.setEmpleado(null);
        }

        // 4. Guardar cambios
        HistorialGastoEntity gastoActualizado = repo.save(gastoExistente);

        // 5. Convertir a DTO
        return convertirAHistorialGastoDTO(gastoActualizado);
    }


    public boolean EliminarHistorialGasto(Long idHistorialGasto) {
        try {
            // Validar existencia del historial antes de eliminar
            HistorialGastoEntity objGasto = repo.findById(idHistorialGasto).orElse(null);

            if (objGasto != null) {
                repo.deleteById(idHistorialGasto);
                return true;
            } else {
                System.out.println("Historial de gasto no encontrado.");
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException(
                    "No se encontró historial con ID: " + idHistorialGasto + " para eliminar.", 1);
        }
    }

    private HistorialGastoDTO convertirAHistorialGastoDTO(HistorialGastoEntity historial) {
        HistorialGastoDTO dto = new HistorialGastoDTO();

        dto.setIdHistorialGasto(historial.getIdHistorialGasto());

        if (historial.getEmpleado() != null) {
            dto.setDuiEmpleado(historial.getEmpleado().getDuiEmpleado());
        }

        dto.setFechaGasto(historial.getFechaGasto());
        dto.setTotalGasto(historial.getTotalGasto());
        dto.setObservaciones(historial.getObservaciones());

        return dto;
    }
}
