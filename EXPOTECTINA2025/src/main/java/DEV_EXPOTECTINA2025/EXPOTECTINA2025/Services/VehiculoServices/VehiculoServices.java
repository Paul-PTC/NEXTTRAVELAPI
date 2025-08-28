package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.VehiculoServices;


import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.VehiculosEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.VehiculoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.VehiculosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehiculoServices {

    private final VehiculosRepository vehiculoRepo;

    // listar
    public Page<VehiculoDTO> listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VehiculosEntities> pageEntiti =vehiculoRepo.findAll(pageable);
        return pageEntiti.map(this::toDTO);
    }

    // obtener por id
    public Optional<VehiculoDTO> obtenerPorId(Long id) {
        return vehiculoRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public VehiculoDTO crear(VehiculoDTO dto) {
        VehiculosEntities e = new VehiculosEntities();
        applyDtoToEntity(dto, e);
        VehiculosEntities saved = vehiculoRepo.save(e);
        return toDTO(saved);
    }

    // actualizar
    @Transactional
    public Optional<VehiculoDTO> actualizar(Long id, VehiculoDTO dto) {
        Optional<VehiculosEntities> opt = vehiculoRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        VehiculosEntities e = opt.get();
        applyDtoToEntity(dto, e);
        VehiculosEntities saved = vehiculoRepo.save(e);
        return Optional.of(toDTO(saved));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!vehiculoRepo.existsById(id)) return false;
        vehiculoRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private VehiculoDTO toDTO(VehiculosEntities e) {
        VehiculoDTO d = new VehiculoDTO();
        d.setIdVehiculo(e.getIdVehiculo());
        d.setPlaca(e.getPlaca());
        d.setMarca(e.getMarca());
        d.setModelo(e.getModelo());
        d.setAnio(e.getAnio());
        d.setFechaVencimientoCirculacion(e.getFechaVencimientoCirculacion());
        d.setFechaVencimientoSeguro(e.getFechaVencimientoSeguro());
        d.setFechaVencimientoRevision(e.getFechaVencimientoRevision());
        return d;
    }

    // aplicar DTO a Entity
    private void applyDtoToEntity(VehiculoDTO d, VehiculosEntities e) {
        e.setPlaca(d.getPlaca());
        e.setMarca(d.getMarca());
        e.setModelo(d.getModelo());
        e.setAnio(d.getAnio());
        e.setFechaVencimientoCirculacion(d.getFechaVencimientoCirculacion());
        e.setFechaVencimientoSeguro(d.getFechaVencimientoSeguro());
        e.setFechaVencimientoRevision(d.getFechaVencimientoRevision());
    }
}