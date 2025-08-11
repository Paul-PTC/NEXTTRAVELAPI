package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.UsuarioServices;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UserEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcpetionUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcpetionUsuarioNoRegistrdo;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.UsuarioYaExisteException;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.UserDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@Transactional
public class UsuarioServices {

    @Autowired
    private UserRepository userRepo;

    // LISTAR
    public List<UserDTO> listar() {
        return userRepo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // OBTENER POR ID
    public UserDTO obtenerPorId(Long id) {
        UserEntity entity = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID: " + id));
        return toDTO(entity);
    }

    // CREAR
    public UserDTO registrar(@Valid UserDTO dto) {
        validarObligatorios(dto);

        // Validaciones de únicos
        if (userRepo.existsByUsuario(dto.getUsuario())) {
            throw new UsuarioYaExisteException("El nombre de usuario ya está en uso");
        }

        if (userRepo.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está en uso");
        }

        try {
            UserEntity entity = toEntity(dto);
            entity.setId(null); // asegurar inserción
            entity = userRepo.save(entity);
            return toDTO(entity);
        } catch (DataIntegrityViolationException e) {
            log.error("Violación de integridad al crear usuario: {}", e.getMessage());
            throw new IllegalArgumentException("Datos duplicados o inválidos al crear el usuario");
        }
    }

    // ACTUALIZAR
    public UserDTO actualizar(Long id, @Valid UserDTO dto) {
        validarObligatorios(dto);

        UserEntity entity = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID: " + id));

        // Unicidad condicional (solo si cambian)
        if (!entity.getUsuario().equals(dto.getUsuario()) && userRepo.existsByUsuario(dto.getUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }
        if (!entity.getCorreo().equals(dto.getCorreo()) && userRepo.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está en uso");
        }

        entity.setUsuario(dto.getUsuario());
        entity.setCorreo(dto.getCorreo());
        entity.setContrasena(dto.getContrasena());
        entity.setRol(dto.getRol());

        try {
            entity = userRepo.save(entity);
            return toDTO(entity);
        } catch (DataIntegrityViolationException e) {
            log.error("Violación de integridad al actualizar usuario {}: {}", id, e.getMessage());
            throw new IllegalArgumentException("Datos duplicados o inválidos al actualizar el usuario");
        }
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        if (!userRepo.existsById(id)) return false;
        userRepo.deleteById(id);
        return true;
    }

    // ===== MAPPERS =====
    private UserDTO toDTO(UserEntity e) {
        UserDTO d = new UserDTO();
        d.setId(e.getId());
        d.setUsuario(e.getUsuario());
        d.setCorreo(e.getCorreo());
        d.setContrasena(e.getContrasena());
        d.setRol(e.getRol());
        return d;
    }

    private UserEntity toEntity(UserDTO d) {
        UserEntity e = new UserEntity();
        e.setId(d.getId());
        e.setUsuario(d.getUsuario());
        e.setCorreo(d.getCorreo());
        e.setContrasena(d.getContrasena());
        e.setRol(d.getRol());
        return e;
    }

    private void validarObligatorios(UserDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El cuerpo de la solicitud no puede ser nulo");
        if (isBlank(dto.getUsuario())) throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        if (isBlank(dto.getCorreo())) throw new IllegalArgumentException("El correo es obligatorio");
        if (isBlank(dto.getContrasena())) throw new IllegalArgumentException("La contraseña es obligatoria");
        if (isBlank(dto.getRol())) throw new IllegalArgumentException("El rol es obligatorio");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
