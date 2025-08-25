package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.TiempoEstiamdoLlegada;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesTiempoLlegada;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TiempoEstimadoLlegadaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TiempoLlegadaDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.TiempoEstimadoRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TiempoLlegadaService {

    @Autowired
    private TiempoEstimadoRepo repo;

    public List<TiempoEstimadoLlegadaDTO> getAllTiemposLlegada() {
        List<EntitesTiempoLlegada> tiempos = repo.findAll();
        return tiempos.stream()
                .map(this::convertirATiempoLlegadaDTO)
                .collect(Collectors.toList());
    }


    public TiempoEstimadoLlegadaDTO InsertarTiempoLlegada(@Valid TiempoLlegadaDTO dto) {
        try {
            EntitesTiempoLlegada entidad = convertirAEntidad(dto);
            EntitesTiempoLlegada guardado = repo.save(entidad);
            return convertirATiempoLlegadaDTO(guardado);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TiempoEstimadoLlegadaDTO ActualizarTiempoLLegada(Long idTiempo, TiempoEstimadoLlegadaDTO tiempoLlegadaDTO)
            throws ExceptionsItinerarioEmpleadoNoEncontrado {

        EntitesTiempoLlegada entidad = repo.findById(idTiempo)
                .orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Tiempo Llegada no encontrado con ID: " + idTiempo));

        entidad.setIdRuta(tiempoLlegadaDTO.getIdRuta());
        entidad.setIdReserva(tiempoLlegadaDTO.getIdReserva());
        entidad.setTiempoEstimado(tiempoLlegadaDTO.getTiempoEstimado() != null ? tiempoLlegadaDTO.getTiempoEstimado().longValue() : null);
        entidad.setFechaHoraCalculo(tiempoLlegadaDTO.getFechaHoraCalculo());

        EntitesTiempoLlegada actualizado = repo.save(entidad);
        return convertirATiempoLlegadaDTO(actualizado);
    }



    public boolean eliminar(Long idTiempo) {
        if (!repo.existsById(idTiempo)) return false;
        repo.deleteById(idTiempo);
        return true;
    }

    private EntitesTiempoLlegada convertirAEntidad(@Valid TiempoLlegadaDTO dto) {
        EntitesTiempoLlegada entidad = new EntitesTiempoLlegada();
        entidad.setIdRuta(dto.getIdRuta());
        entidad.setIdReserva(dto.getIdReserva());
        entidad.setTiempoEstimado(dto.getTiempoEstimado() != null ? dto.getTiempoEstimado().longValue() : null);
        entidad.setFechaHoraCalculo(dto.getFechaHoraCalculo());
        return entidad;
    }

    // Conversión Entidad -> DTO
    private TiempoEstimadoLlegadaDTO convertirATiempoLlegadaDTO(EntitesTiempoLlegada entidad) {
        TiempoEstimadoLlegadaDTO dto = new TiempoEstimadoLlegadaDTO();
        dto.setIdTiempoestimado(entidad.getIdTiempoEstimado());
        dto.setIdRuta(entidad.getIdRuta());
        dto.setIdReserva(entidad.getIdReserva());
        dto.setTiempoEstimado(entidad.getTiempoEstimado() != null ? entidad.getTiempoEstimado().intValue() : null);
        dto.setFechaHoraCalculo(entidad.getFechaHoraCalculo());
        return dto;
    }
  

}
