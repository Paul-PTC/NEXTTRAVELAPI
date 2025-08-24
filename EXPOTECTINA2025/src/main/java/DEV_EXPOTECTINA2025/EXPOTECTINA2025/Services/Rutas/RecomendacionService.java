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

    private RecomendacionRepository recomendacionRepo;
    private ClienteRepository clienteRepository;

    // listar
    public List<RecomendacionDTO> listar() {
        return recomendacionRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
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
        RecomendacionEntities saved = recomendacionRepo.save(e);
        return toDTO(saved);
    }

    // actualizar
    @Transactional
    public Optional<RecomendacionDTO> actualizar(Long id, RecomendacionDTO dto) {
        Optional<RecomendacionEntities> opt = recomendacionRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        RecomendacionEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        RecomendacionEntities saved = recomendacionRepo.save(e);
        return Optional.of(toDTO(saved));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!recomendacionRepo.existsById(id)) return false;
        recomendacionRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private RecomendacionDTO toDTO(RecomendacionEntities e) {
        RecomendacionDTO dto = new RecomendacionDTO();
        dto.setIdRecomendacion(e.getIdRecomendacion());
        dto.setDuiCliente(e.getCliente() != null ? e.getCliente().getDuiCliente() : null);
        dto.setTipoLugar(e.getTipoLugar());
        dto.setLugarSugerido(e.getLugarSugerido());
        dto.setDescripcion(e.getDescripcion());
        return dto;
    }

    // aplicar datos del DTO a la Entity
    private void applyDtoToEntity(RecomendacionDTO dto, RecomendacionEntities e, boolean isUpdate) {
        if (dto.getDuiCliente() != null || !isUpdate) {
            if (dto.getDuiCliente() != null) {
                ClienteEntities cliente = clienteRepository.findById(dto.getDuiCliente())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No se encontró el cliente con DUI: " + dto.getDuiCliente()));
                e.setCliente(cliente);
            } else {
                e.setCliente(null);
            }
        }
        e.setTipoLugar(dto.getTipoLugar());
        e.setLugarSugerido(dto.getLugarSugerido());
        e.setDescripcion(dto.getDescripcion());
    }

}
