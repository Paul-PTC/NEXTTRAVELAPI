package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.FeedbackEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsFeedbackNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.FeedbackDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.FeedbackRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FeedbackServices {

    @Autowired
    private FeedbackRepository repoFeedback;

    @Autowired
    private ReservaRepository repoReserva;


    public List<FeedbackDTO> getAllFeedbacks() {
        List<FeedbackEntity> feedbacks = repoFeedback.findAll();
        return feedbacks.stream()
                .map(this::convertirAFeedbackDTO)
                .collect(Collectors.toList());
    }

    private FeedbackDTO convertirAFeedbackDTO(FeedbackEntity entidad) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setIdFeedback(entidad.getIdFeedback());
        dto.setComentario(entidad.getComentario());
        dto.setFechaComentario(entidad.getFechaComentario());

        if (entidad.getReserva() != null) {
            dto.setIdReserva(entidad.getReserva().getId());
        }

        return dto;
    }
    public FeedbackDTO insertFeedback(FeedbackDTO feedbackDto) {
        if (feedbackDto == null || feedbackDto.getComentario() == null || feedbackDto.getComentario().isBlank()) {
            throw new IllegalArgumentException("El feedback o el comentario no pueden ser nulos o vacíos");
        }

        try {
            FeedbackEntity feedbackEntity = convertirAFeedbackEntity(feedbackDto);
            FeedbackEntity feedbackGuardado = repoFeedback.save(feedbackEntity);
            return convertirAFeedbackDTO(feedbackGuardado);
        } catch (Exception e) {
            log.error("Error al registrar feedback: " + e.getMessage());
            throw new RuntimeException("Error al registrar el feedback: " + e.getMessage());
        }
    }

    private FeedbackEntity convertirAFeedbackEntity(FeedbackDTO dto) {
        FeedbackEntity entity = new FeedbackEntity();
        entity.setComentario(dto.getComentario());
        entity.setFechaComentario(dto.getFechaComentario());

        ReservaEntities reserva = repoReserva.findById(dto.getIdReserva())
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + dto.getIdReserva()));
        entity.setReserva(reserva);

        return entity;
    }
    public FeedbackDTO actualizarFeedback(Long id, FeedbackDTO feedbackDto) {
        // 1. Verificar existencia
        FeedbackEntity feedbackExistente = repoFeedback.findById(id)
                .orElseThrow(() -> new ExceptionsFeedbackNoEncontrado("Feedback no encontrado"));

        // 2. Actualizar campos
        feedbackExistente.setComentario(feedbackDto.getComentario());
        feedbackExistente.setFechaComentario(feedbackDto.getFechaComentario());

        // 3. Actualizar relación con Reserva
        if (feedbackDto.getIdReserva() != null) {
            ReservaEntities reserva = repoReserva.findById(feedbackDto.getIdReserva())
                    .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID proporcionado"));
            feedbackExistente.setReserva(reserva);
        } else {
            feedbackExistente.setReserva(null);
        }

        // 4. Guardar cambios
        FeedbackEntity feedbackActualizado = repoFeedback.save(feedbackExistente);

        // 5. Convertir a DTO
        return convertirAFeedbackDTO(feedbackActualizado);
    }
    public boolean removerFeedback(Long id) {
        try {
            FeedbackEntity feedback = repoFeedback.findById(id).orElse(null);
            if (feedback != null) {
                repoFeedback.deleteById(id);
                return true;
            } else {
                System.out.println("Feedback no encontrado.");
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("No se encontró feedback con ID: " + id + " para eliminar.", 1);
        }
    }


}

