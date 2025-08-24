package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Pago;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.PuntosClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.PuntosClienteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.PuntosClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PuntosClienteService {

    @Autowired
    private PuntosClienteRepository puntosRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    // LISTAR
    public List<PuntosClienteDTO> obtenerTodos() {
        return puntosRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // OBTENER POR ID
    public PuntosClienteDTO obtenerPorId(Long id) {
        PuntosClienteEntities e = puntosRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron puntos con ID: " + id));
        return toDTO(e);
    }

    // CREAR
    public PuntosClienteDTO registrar(@Valid PuntosClienteDTO dto) {
        ClienteEntities cliente = clienteRepo.findById(dto.getDuiCliente())
                .orElseThrow(() -> new EntityNotFoundException("No existe cliente con DUI: " + dto.getDuiCliente()));

        PuntosClienteEntities e = new PuntosClienteEntities();
        e.setIdPuntos(null); // asegurar inserción
        e.setCliente(cliente);
        e.setPuntosAcumulados(dto.getPuntosAcumulados());
        e.setPuntosCanjeados(dto.getPuntosCanjeados());
        e.setUltimaActualizacion(dto.getUltimaActualizacion());

        e = puntosRepo.save(e);
        return toDTO(e);
    }

    // ACTUALIZAR
    public PuntosClienteDTO actualizar(Long id, @Valid PuntosClienteDTO dto) {
        PuntosClienteEntities e = puntosRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron puntos con ID: " + id));

        ClienteEntities cliente = clienteRepo.findById(dto.getDuiCliente())
                .orElseThrow(() -> new EntityNotFoundException("No existe cliente con DUI: " + dto.getDuiCliente()));

        e.setCliente(cliente);
        e.setPuntosAcumulados(dto.getPuntosAcumulados());
        e.setPuntosCanjeados(dto.getPuntosCanjeados());
        e.setUltimaActualizacion(dto.getUltimaActualizacion());

        e = puntosRepo.save(e);
        return toDTO(e);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        if (!puntosRepo.existsById(id)) return false;
        puntosRepo.deleteById(id);
        return true;
    }

    // ====== MAPPER ======
    private PuntosClienteDTO toDTO(PuntosClienteEntities e) {
        PuntosClienteDTO d = new PuntosClienteDTO();
        d.setIdPuntos(e.getIdPuntos());
        d.setPuntosAcumulados(e.getPuntosAcumulados());
        d.setPuntosCanjeados(e.getPuntosCanjeados());
        d.setUltimaActualizacion(e.getUltimaActualizacion());
        if (e.getCliente() != null) d.setDuiCliente(e.getCliente().getDuiCliente());
        return d;
    }
}
