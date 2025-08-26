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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RutasGuardadasService {

    private final RutasGuardadasRepository rutasGuardadasRepo;
    private final ClienteRepository clienteRepo;
    private final RutaRepository rutaRepo;

    // listar
    public List<RutasGuardadasDTO> listar() {
        return rutasGuardadasRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // obtener por id
    public Optional<RutasGuardadasDTO> obtenerPorId(Long id) {
        return rutasGuardadasRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public RutasGuardadasDTO crear(RutasGuardadasDTO dto) {
        RutasGuardadasEntity e = new RutasGuardadasEntity();
        applyDtoToEntity(dto, e, false);
        return toDTO(rutasGuardadasRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<RutasGuardadasDTO> actualizar(Long id, RutasGuardadasDTO dto) {
        Optional<RutasGuardadasEntity> opt = rutasGuardadasRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        RutasGuardadasEntity e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(rutasGuardadasRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!rutasGuardadasRepo.existsById(id)) return false;
        rutasGuardadasRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private RutasGuardadasDTO toDTO(RutasGuardadasEntity e) {
        RutasGuardadasDTO d = new RutasGuardadasDTO();
        d.setIdRutaGuardada(e.getIdRutaGuardada());
        d.setDuiCliente(e.getCliente() != null ? e.getCliente().getDuiCliente() : null);
        d.setIdRuta(e.getRuta() != null ? e.getRuta().getIdRuta() : null);
        d.setNombreRuta(e.getNombreRuta());
        d.setFechaGuardado(e.getFechaGuardado());
        return d;
    }

    // aplicar DTO a Entity
    private void applyDtoToEntity(RutasGuardadasDTO d, RutasGuardadasEntity e, boolean isUpdate) {
        if (d.getDuiCliente() != null || !isUpdate) {
            if (d.getDuiCliente() != null) {
                ClienteEntities c = clienteRepo.findById(d.getDuiCliente())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Cliente con DUI: " + d.getDuiCliente()));
                e.setCliente(c);
            } else {
                e.setCliente(null);
            }
        }
        if (d.getIdRuta() != null || !isUpdate) {
            if (d.getIdRuta() != null) {
                RutaEntities r = rutaRepo.findById(d.getIdRuta())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Ruta con ID: " + d.getIdRuta()));
                e.setRuta(r);
            } else {
                e.setRuta(null);
            }
        }
        e.setNombreRuta(d.getNombreRuta());
        e.setFechaGuardado(d.getFechaGuardado());
    }
}


