package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Pago;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.PuntosClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.PuntosClienteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.PuntosClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PuntosClienteService {

    private final PuntosClienteRepository puntosRepo;
    private final ClienteRepository clienteRepo; // PK = String (DUI)

    // listar
    public List<PuntosClienteDTO> listar() {
        return puntosRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // obtener por id
    public Optional<PuntosClienteDTO> obtenerPorId(Long id) {
        return puntosRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public PuntosClienteDTO crear(PuntosClienteDTO dto) {
        PuntosClienteEntities e = new PuntosClienteEntities();
        applyDtoToEntity(dto, e, false);
        return toDTO(puntosRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<PuntosClienteDTO> actualizar(Long id, PuntosClienteDTO dto) {
        Optional<PuntosClienteEntities> opt = puntosRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        PuntosClienteEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        return Optional.of(toDTO(puntosRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!puntosRepo.existsById(id)) return false;
        puntosRepo.deleteById(id);
        return true;
    }

    // mapper entity -> dto
    private PuntosClienteDTO toDTO(PuntosClienteEntities e) {
        PuntosClienteDTO d = new PuntosClienteDTO();
        d.setIdPuntos(e.getIdPuntos());
        d.setDuiCliente(e.getCliente() != null ? e.getCliente().getDuiCliente() : null);
        d.setPuntosAcumulados(e.getPuntosAcumulados());
        d.setPuntosCanjeados(e.getPuntosCanjeados());
        d.setUltimaActualizacion(e.getUltimaActualizacion());
        return d;
    }

    // aplicar dto -> entity
    private void applyDtoToEntity(PuntosClienteDTO d, PuntosClienteEntities e, boolean isUpdate) {
        if (d.getDuiCliente() != null || !isUpdate) {
            if (d.getDuiCliente() != null) {
                ClienteEntities cliente = clienteRepo.findById(d.getDuiCliente())
                        .orElseThrow(() -> new EntityNotFoundException("No existe Cliente con DUI: " + d.getDuiCliente()));
                e.setCliente(cliente);
            } else {
                e.setCliente(null);
            }
        }
        e.setPuntosAcumulados(d.getPuntosAcumulados());
        e.setPuntosCanjeados(d.getPuntosCanjeados());
        e.setUltimaActualizacion(d.getUltimaActualizacion());
    }
}