package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RecomendacionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RecomendacionDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RecomendacionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecomendacionService {

    private final RecomendacionRepository recomendacionRepo;
    private final ClienteRepository clienteRepo;

    // listar
    public List<RecomendacionDTO> listar() {
        return recomendacionRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // obtener por id
    public Optional<RecomendacionDTO> obtenerPorId(Long id) {
        return recomendacionRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public RecomendacionDTO crear(RecomendacionDTO dto) {
        RecomendacionEntities e = new RecomendacionEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(recomendacionRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<RecomendacionDTO> actualizar(Long id, RecomendacionDTO dto) {
        Optional<RecomendacionEntities> opt = recomendacionRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        RecomendacionEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(recomendacionRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!recomendacionRepo.existsById(id)) return false;
        recomendacionRepo.deleteById(id);
        return true;
    }

    // mapeo entity -> dto
    private RecomendacionDTO toDTO(RecomendacionEntities e) {
        RecomendacionDTO d = new RecomendacionDTO();
        d.setIdRecomendacion(e.getIdRecomendacion());
        d.setDuiCliente(e.getCliente() != null ? e.getCliente().getDuiCliente() : null);
        d.setTipoLugar(e.getTipoLugar());
        d.setLugarSugerido(e.getLugarSugerido());
        d.setDescripcion(e.getDescripcion());
        return d;
    }

    // aplicar dto -> entity
    private void applyDtoToEntity(RecomendacionDTO d, RecomendacionEntities e, boolean isUpdate) {
        if (d.getDuiCliente() != null || !isUpdate) {
            if (d.getDuiCliente() != null) {
                ClienteEntities c = clienteRepo.findById(d.getDuiCliente())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Cliente con DUI: " + d.getDuiCliente()));
                e.setCliente(c);
            } else {
                e.setCliente(null);
            }
        }
        e.setTipoLugar(d.getTipoLugar());
        e.setLugarSugerido(d.getLugarSugerido());
        e.setDescripcion(d.getDescripcion());
    }
}
