package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.TiempoEstiamdoLlegada;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesTiempoLlegada;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ReservaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TiempoLlegadaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.TiempoEstimadoRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TiempoLlegadaService {

    private final TiempoEstimadoRepo tiempoRepo;
    private final RutaRepository rutaRepo;
    private final ReservaRepository reservaRepo;

    // Obtener todos los registros
    public List<TiempoLlegadaDTO> getAllTiempos() {
        log.info("Listando todos los tiempos estimados");
        return tiempoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Buscar por ID
    public TiempoLlegadaDTO getById(Long idTiempo) {
        log.info("Buscando tiempo estimado id={}", idTiempo);
        return tiempoRepo.findById(idTiempo)
                .map(this::toDTO)
                .orElse(null);
    }

    // Insertar nuevo tiempo estimado
    @Transactional
    public TiempoLlegadaDTO insertarTiempoLlegada(TiempoLlegadaDTO dto) {
        log.info("Insertando tiempo estimado {}", dto);

        RutaEntities ruta = rutaRepo.findById(dto.getIdRuta())
                .orElseThrow(() -> new IllegalArgumentException("IDRUTA inválido o no existe"));
        ReservaEntities reserva = reservaRepo.findById(dto.getIdReserva())
                .orElseThrow(() -> new IllegalArgumentException("IDRESERVA inválido o no existe"));

        EntitesTiempoLlegada entity = new EntitesTiempoLlegada();
        entity.setIdRuta(ruta.getIdRuta());
        entity.setIdReserva(reserva.getId());
        entity.setTiempoEstimado(dto.getTiempoEstimado());

        LocalDateTime fecha = (dto.getFechaHoraCalculo() != null)
                ? dto.getFechaHoraCalculo()
                : LocalDateTime.now();

        entity.setFechaHoraCalculo(fecha);

        EntitesTiempoLlegada saved = tiempoRepo.save(entity);
        return toDTO(saved);
    }

    // Actualizar tiempo estimado
    @Transactional
    public TiempoLlegadaDTO actualizarTiempoLlegada(Long idTiempo, TiempoLlegadaDTO dto) {
        log.info("Actualizando tiempo estimado id={} dto={}", idTiempo, dto);

        Optional<EntitesTiempoLlegada> opt = tiempoRepo.findById(idTiempo);
        if (opt.isEmpty()) return null;

        EntitesTiempoLlegada entity = opt.get();

        // Actualiza relaciones si vienen en el DTO
        if (dto.getIdRuta() != null) {
            RutaEntities ruta = rutaRepo.findById(dto.getIdRuta())
                    .orElseThrow(() -> new IllegalArgumentException("IDRUTA inválido o no existe"));
            entity.setIdRuta(ruta.getIdRuta());
        }
        if (dto.getIdReserva() != null) {
            ReservaEntities reserva = reservaRepo.findById(dto.getIdReserva())
                    .orElseThrow(() -> new IllegalArgumentException("IDRESERVA inválido o no existe"));
            entity.setIdReserva(reserva.getId());
        }

        if (dto.getTiempoEstimado() != null) {
            entity.setTiempoEstimado(dto.getTiempoEstimado());
        }
        if (dto.getFechaHoraCalculo() != null) {
            entity.setFechaHoraCalculo(dto.getFechaHoraCalculo());
        }

        EntitesTiempoLlegada updated = tiempoRepo.save(entity);
        return toDTO(updated);
    }

    // Eliminar tiempo estimado
    @Transactional
    public boolean eliminarTiempoLlegada(Long idTiempo) {
        log.info("Eliminando tiempo estimado id={}", idTiempo);
        Optional<EntitesTiempoLlegada> opt = tiempoRepo.findById(idTiempo);
        if (opt.isEmpty()) return false;
        tiempoRepo.delete(opt.get());
        return true;
    }

    // Conversión Entity -> DTO
    private TiempoLlegadaDTO toDTO(EntitesTiempoLlegada e) {
        TiempoLlegadaDTO dto = new TiempoLlegadaDTO();
        dto.setIdTiempoestimado(e.getIdTiempoEstimado());
        dto.setIdRuta(e.getIdRuta() != null ? e.getIdRuta() : null);
        dto.setIdReserva(e.getIdReserva() != null ? e.getIdReserva() : null);
        dto.setTiempoEstimado(e.getTiempoEstimado());
        dto.setFechaHoraCalculo(e.getFechaHoraCalculo());
        return dto;
    }
}