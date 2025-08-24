package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.EstadoViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.TiempoEstimadoLlegadaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TiempoEstimadoLlegadaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.TiempoEstimadoLlegadaRepository;
import jakarta.persistence.EntityNotFoundException;
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
public class TiempoEstimadoLlegadaService {

    private TiempoEstimadoLlegadaRepository tiempoRepo;
    private RutaRepository rutaRepo;
    private ReservaRepository reservaRepo;

    // listar
    public List<TiempoEstimadoLlegadaDTO> listar() {
        return tiempoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // obtener por id
    public Optional<TiempoEstimadoLlegadaDTO> obtenerPorId(Long id) {
        return tiempoRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public TiempoEstimadoLlegadaDTO crear(TiempoEstimadoLlegadaDTO dto) {
        TiempoEstimadoLlegadaEntities e = new TiempoEstimadoLlegadaEntities();
        applyDtoToEntity(dto, e, false);
        TiempoEstimadoLlegadaEntities saved = tiempoRepo.save(e);
        return toDTO(saved);
    }

    // actualizar
    @Transactional
    public Optional<TiempoEstimadoLlegadaDTO> actualizar(Long id, TiempoEstimadoLlegadaDTO dto) {
        Optional<TiempoEstimadoLlegadaEntities> opt = tiempoRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        TiempoEstimadoLlegadaEntities e = opt.get();
        applyDtoToEntity(dto, e, true);
        TiempoEstimadoLlegadaEntities saved = tiempoRepo.save(e);
        return Optional.of(toDTO(saved));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!tiempoRepo.existsById(id)) return false;
        tiempoRepo.deleteById(id);
        return true;
    }

    // mapeo a DTO
    private TiempoEstimadoLlegadaDTO toDTO(TiempoEstimadoLlegadaEntities e) {
        TiempoEstimadoLlegadaDTO dto = new TiempoEstimadoLlegadaDTO();
        dto.setIdTiempoEstimado(e.getIdTiempoEstimado());
        dto.setIdRuta(e.getRuta() != null ? e.getRuta().getIdRuta() : null);
        dto.setIdReserva(e.getReserva() != null ? e.getReserva().getId() : null);
        dto.setTiempoEstimadoMinutos(e.getTiempoEstimadoMinutos());
        dto.setFechaHoraCalculo(e.getFechaHoraCalculo());
        return dto;
    }

    // aplicar datos del DTO a la Entity
    private void applyDtoToEntity(TiempoEstimadoLlegadaDTO dto, TiempoEstimadoLlegadaEntities e, boolean isUpdate) {
        if (dto.getIdRuta() != null || !isUpdate) {
            if (dto.getIdRuta() != null) {
                RutaEntities ruta = rutaRepo.findById(dto.getIdRuta())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No existe Ruta con ID: " + dto.getIdRuta()));
                e.setRuta(ruta);
            } else {
                e.setRuta(null);
            }
        }

        if (dto.getIdReserva() != null || !isUpdate) {
            if (dto.getIdReserva() != null) {
                ReservaEntities reserva = reservaRepo.findById(dto.getIdReserva())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No existe Reserva con ID: " + dto.getIdReserva()));
                e.setReserva(reserva);
            } else {
                e.setReserva(null);
            }
        }

        e.setTiempoEstimadoMinutos(dto.getTiempoEstimadoMinutos());
        e.setFechaHoraCalculo(dto.getFechaHoraCalculo());
    }
}
