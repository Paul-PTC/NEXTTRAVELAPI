package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Chat;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.CodigoVerificacionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UserEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.CodigoVerificacionDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.CodigoVerificacionRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CodigoVerificacionService {

    @Autowired
    private CodigoVerificacionRepository codigoRepo;

    @Autowired
    private UserRepository userRepo;

    // LISTAR
    public List<CodigoVerificacionDTO> obtenerTodos() {
        return codigoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public CodigoVerificacionDTO obtenerPorId(Long id) {
        CodigoVerificacionEntities e = codigoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el código con ID: " + id));
        return toDTO(e);
    }

    // CREAR
    public CodigoVerificacionDTO registrar(@Valid CodigoVerificacionDTO dto) {
        UserEntity usuario = userRepo.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("No existe Usuario con ID: " + dto.getIdUsuario()));

        CodigoVerificacionEntities e = new CodigoVerificacionEntities();
        e.setIdCodigo(null); // asegurar inserción (si usas secuencia)
        e.setUsuario(usuario);
        e.setCodigo(dto.getCodigo());
        e.setFechaGenerado(dto.getFechaGenerado());
        e.setUsado(dto.getUsado()); // 'S' / 'N'

        e = codigoRepo.save(e);
        return toDTO(e);
    }

    // ACTUALIZAR
    public CodigoVerificacionDTO actualizar(Long id, @Valid CodigoVerificacionDTO dto) {
        CodigoVerificacionEntities e = codigoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el código con ID: " + id));

        UserEntity usuario = userRepo.findById(dto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("No existe Usuario con ID: " + dto.getIdUsuario()));

        e.setUsuario(usuario);
        e.setCodigo(dto.getCodigo());
        e.setFechaGenerado(dto.getFechaGenerado());
        e.setUsado(dto.getUsado());

        e = codigoRepo.save(e);
        return toDTO(e);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        if (!codigoRepo.existsById(id)) return false;
        codigoRepo.deleteById(id);
        return true;
    }

    // ====== MAPPER ======
    private CodigoVerificacionDTO toDTO(CodigoVerificacionEntities e) {
        CodigoVerificacionDTO d = new CodigoVerificacionDTO();
        d.setIdCodigo(e.getIdCodigo());
        d.setCodigo(e.getCodigo());
        d.setFechaGenerado(e.getFechaGenerado());
        d.setUsado(e.getUsado());
        if (e.getUsuario() != null) d.setIdUsuario(e.getUsuario().getId());
        return d;
    }
}
