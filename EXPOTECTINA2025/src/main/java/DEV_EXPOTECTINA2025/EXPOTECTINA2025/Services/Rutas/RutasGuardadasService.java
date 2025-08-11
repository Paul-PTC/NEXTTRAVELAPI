package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutasGuardadasEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RutasGuardadasDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutasGuardadasRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RutasGuardadasService {

    @Autowired
    private RutasGuardadasRepository rutasRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private RutaRepository rutaRepo;

    // GET: listar todo
    public List<RutasGuardadasDTO> obtenerTodas() {
        return rutasRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // POST: crear
    public RutasGuardadasDTO registrar(@Valid RutasGuardadasDTO dto) {
        if (dto == null) throw new IllegalArgumentException("El DTO no puede ser nulo");
        if (dto.getDuiCliente() == null || dto.getDuiCliente().isBlank())
            throw new IllegalArgumentException("El DUI del cliente es obligatorio");
        if (dto.getIdRuta() == null)
            throw new IllegalArgumentException("El ID de la ruta es obligatorio");

        try {
            ClienteEntities cliente = clienteRepo.findById(dto.getDuiCliente())
                    .orElseThrow(() -> new EntityNotFoundException("No existe cliente con DUI: " + dto.getDuiCliente()));

            RutaEntities ruta = rutaRepo.findById(dto.getIdRuta())
                    .orElseThrow(() -> new EntityNotFoundException("No existe ruta con ID: " + dto.getIdRuta()));

            RutasGuardadasEntity entity = new RutasGuardadasEntity();
            entity.setIdRutaGuardada(null); // asegurar inserción
            entity.setNombreRuta(dto.getNombreRuta());
            entity.setFechaGuardado(dto.getFechaGuardado());
            entity.setCliente(cliente);
            entity.setRuta(ruta);

            entity = rutasRepo.save(entity);
            return toDTO(entity);

        } catch (Exception e) {
            log.error("Error al registrar ruta guardada: {}", e.getMessage());
            throw new RuntimeException("Error al registrar la ruta guardada: " + e.getMessage());
        }
    }

    // PUT: actualizar
    public RutasGuardadasDTO actualizar(Long id, @Valid RutasGuardadasDTO dto) {
        RutasGuardadasEntity entity = rutasRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la ruta guardada con ID: " + id));

        // Validar y setear cliente
        ClienteEntities cliente = clienteRepo.findById(dto.getDuiCliente())
                .orElseThrow(() -> new EntityNotFoundException("No existe cliente con DUI: " + dto.getDuiCliente()));

        // Validar y setear ruta
        RutaEntities ruta = rutaRepo.findById(dto.getIdRuta())
                .orElseThrow(() -> new EntityNotFoundException("No existe ruta con ID: " + dto.getIdRuta()));

        entity.setNombreRuta(dto.getNombreRuta());
        entity.setFechaGuardado(dto.getFechaGuardado());
        entity.setCliente(cliente);
        entity.setRuta(ruta);

        entity = rutasRepo.save(entity);
        return toDTO(entity);
    }

    // DELETE: eliminar
    public boolean eliminar(Long id) {
        try {
            if (!rutasRepo.existsById(id)) return false;
            rutasRepo.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException(
                    "No se encontró ruta guardada con ID: " + id + " para eliminar.", 1);
        }
    }

    // ====== MAPPERS ======
    private RutasGuardadasDTO toDTO(RutasGuardadasEntity e) {
        RutasGuardadasDTO d = new RutasGuardadasDTO();
        d.setIdRutasGuardadas(e.getIdRutaGuardada());
        d.setNombreRuta(e.getNombreRuta());
        d.setFechaGuardado(e.getFechaGuardado());
        if (e.getCliente() != null) d.setDuiCliente(e.getCliente().getDuiCliente());
        if (e.getRuta() != null) d.setIdRuta(e.getRuta().getIdRuta());
        return d;
    }
}


