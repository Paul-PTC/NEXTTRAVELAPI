package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.CalificacionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.CalificacionDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.CalificacionRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CalificacionServices {

    @Autowired
    private CalificacionRepository repo;

    @Autowired
    private ReservaRepository reservaRepository;

    // Obtener todas las calificaciones
    public List<CalificacionDTO> getAllCalificaciones() {
        List<CalificacionEntities> Calificaciones = repo.findAll();
        return Calificaciones.stream()
                .map(this::convertirACalificacionDTO)
                .collect(Collectors.toList());
    }



    // Conversión de Entity a DTO
    private CalificacionDTO convertirACalificacionDTO(CalificacionEntities entidad) {
        CalificacionDTO dto = new CalificacionDTO();

        // Extraemos el ID de la entidad reserva asociada
        if (entidad.getReserva() != null) {
            dto.setIdReserva(Long.valueOf(entidad.getReserva().getId()));
        }
        dto.setIdCalificacion(entidad.getIdCalificacion());
        dto.setCalificacion(entidad.getCalificacion());
        dto.setFechaCalificacion(entidad.getFechaCalificacion());

        return dto;
    }

    public CalificacionDTO insertarCalificaciones(@Valid CalificacionDTO calificacionDTO) {
        // Validación básica: asegúrate de que venga el ID de la reserva y el valor de la calificación
        if (calificacionDTO == null || calificacionDTO.getIdReserva() == null || calificacionDTO.getCalificacion() == null) {
            throw new IllegalArgumentException("La calificación y la reserva no pueden ser nulas.");
        }

        try {
            // Buscamos la reserva asociada en la base de datos
            Optional<ReservaEntities> reservaOptional = reservaRepository.findById(Math.toIntExact(calificacionDTO.getIdReserva()));
            if (reservaOptional.isEmpty()) {
                throw new EntityNotFoundException("No se encontró la reserva con ID: " + calificacionDTO.getIdReserva());
            }

            // Convertimos el DTO en entidad y le asignamos la reserva encontrada
            CalificacionEntities entidad = new CalificacionEntities();
            entidad.setReserva(reservaOptional.get());
            entidad.setCalificacion(calificacionDTO.getCalificacion());
            entidad.setFechaCalificacion(calificacionDTO.getFechaCalificacion());

            // Guardamos en la base de datos
            CalificacionEntities guardada = repo.save(entidad);

            // Devolvemos la respuesta como DTO
            return convertirACalificacionDTO(guardada);

        } catch (Exception e) {
            log.error("Error al insertar calificación: " + e.getMessage());
            throw new RuntimeException("Error al registrar la calificación: " + e.getMessage());
        }
    }

    public CalificacionDTO actualizarCalificacion(Long id, CalificacionDTO calificacionDTO) {
        CalificacionEntities entidad = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la calificación con ID: " + id));

        // Verificamos que la nueva reserva exista si es que se quiere cambiar
        Optional<ReservaEntities> reservaOptional = reservaRepository.findById(Math.toIntExact(calificacionDTO.getIdReserva()));
        if (reservaOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontró la reserva con ID: " + calificacionDTO.getIdReserva());
        }

        entidad.setReserva(reservaOptional.get());
        entidad.setCalificacion(calificacionDTO.getCalificacion());
        entidad.setFechaCalificacion(calificacionDTO.getFechaCalificacion());

        CalificacionEntities actualizada = repo.save(entidad);
        return convertirACalificacionDTO(actualizada);
    }

    public boolean eliminarCalificacion(Long id) {
        try {
            // Se valida la existencia de la calificación antes de eliminarla
            CalificacionEntities calificacion = repo.findById(id).orElse(null);

            if (calificacion != null) {
                repo.deleteById(id);
                return true;
            } else {
                System.out.println("Calificación no encontrada.");
                return false;
            }

        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("No se encontró calificación con ID: " + id + " para eliminar.", 1);
        }
    }

}
