package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.LugarTuristicoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RutaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.LugarTuristicoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RutaService {

    private final RutaRepository rutaRepo;
    private final LugarTuristicoRepository lugarRepo;

    // listar
    public List<RutaDTO> listar() {
        return rutaRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<RutaDTO> obtenerPorId(Long id) {
        return rutaRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public RutaDTO crear(RutaDTO dto) {
        RutaEntities e = new RutaEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(rutaRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<RutaDTO> actualizar(Long id, RutaDTO dto) {
        Optional<RutaEntities> opt = rutaRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        RutaEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(rutaRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!rutaRepo.existsById(id)) return false;
        rutaRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private RutaDTO toDTO(RutaEntities e) {
        RutaDTO d = new RutaDTO();
        d.setIdRuta(e.getIdRuta());
        d.setIdLugar(e.getLugar() != null ? e.getLugar().getIdLugar() : null);
        d.setOrigen(e.getOrigen());
        d.setDestino(e.getDestino());
        d.setDistanciaKm(e.getDistanciaKm());
        d.setDuracionEstimada(e.getDuracionEstimada());
        d.setDescripcion(e.getDescripcion());
        d.setEstado(e.getEstado());
        return d;
    }

    // aplicar DTO a Entity
    private void applyDtoToEntity(RutaDTO d, RutaEntities e, boolean isUpdate) {
        if (d.getIdLugar() != null || !isUpdate) {
            if (d.getIdLugar() != null) {
                LugarTuristicoEntity lugar = lugarRepo.findById(d.getIdLugar())
                        .orElseThrow(() -> new EntityNotFoundException("No existe LugarTuristico con ID: " + d.getIdLugar()));
                e.setLugar(lugar);
            } else {
                e.setLugar(null);
            }
        }
        e.setOrigen(d.getOrigen());
        e.setDestino(d.getDestino());
        e.setDistanciaKm(d.getDistanciaKm());
        e.setDuracionEstimada(d.getDuracionEstimada());
        e.setDescripcion(d.getDescripcion());
        e.setEstado(d.getEstado());
    }
}
