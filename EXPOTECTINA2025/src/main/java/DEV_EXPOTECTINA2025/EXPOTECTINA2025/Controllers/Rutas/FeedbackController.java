package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcepcionDatosDuplicados;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsFeedbackNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.FeedbackDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas.FeedbackServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiFeedback")
public class FeedbackController {

    @Autowired
    private FeedbackServices servicio;

    @GetMapping("/feedbacks")
    public List<FeedbackDTO> obtenerFeedbacks() {
        return servicio.getAllFeedbacks();
    }
    @PostMapping("/ingresarFeedback")
    public ResponseEntity<Map<String, Object>> registrarFeedback(@Valid @RequestBody FeedbackDTO feedback, HttpServletRequest request) {
        try {
            FeedbackDTO respuesta = servicio.insertFeedback(feedback);
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del feedback inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", respuesta
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar feedback",
                            "detail", e.getMessage()
                    ));
        }
    }
    @PutMapping("/modificarFeedback/{id}")
    public ResponseEntity<?> modificarFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackDTO feedback,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            FeedbackDTO feedbackActualizado = servicio.actualizarFeedback(id, feedback);
            return ResponseEntity.ok(feedbackActualizado);
        }

        catch (ExceptionsFeedbackNoEncontrado e) {
            return ResponseEntity.notFound().build();
        }

        catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }
    }

}

