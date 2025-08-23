package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.TiempoEstiamdoLlegada;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesTiempoLlegada;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPago;
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

    public List<TiempoLlegadaDTO> getAllTiempos() {
        List<EntitesTiempoLlegada> ItiEmp = repo.findAll();
        return ItiEmp.stream()
                .map(this::convertirATiempoLlegadaDTO)
                .collect(Collectors.toList());
    }

    private TiempoLlegadaDTO convertirATiempoLlegadaDTO(EntitesTiempoLlegada tiempo) {
        TiempoLlegadaDTO dtoTiempo = new TiempoLlegadaDTO();
        dtoTiempo.setIdTiempoestimado(tiempo.getIdTiempoestimado());
        dtoTiempo.setIdRuta(tiempo.getIdRuta());
        dtoTiempo.setIdReserva(tiempo.getIdReserva());
        dtoTiempo.setTiempoEstimado(tiempo.getTiempoEstimado());
        dtoTiempo.setFechahoraCalculo(tiempo.getFechahoraCalculo());

        return dtoTiempo;
    }


    public TiempoLlegadaDTO InsertarTiempoLlegada(@Valid TiempoLlegadaDTO tiempo) {
        try {
            EntitesTiempoLlegada insertTiempo = new EntitesTiempoLlegada();
                //Paul aqui nose como seria, por favor ayudame
            insertTiempo.setTiempoEstimado(tiempo.getTiempoEstimado());
            insertTiempo.setFechahoraCalculo(tiempo.getFechahoraCalculo());

            repo.save(insertTiempo);
            return tiempo;
        } catch (Exception e) {
            return null;
        }
    }

    public TiempoLlegadaDTO ActualizarTiempoLLegada(Long idtiempo, TiempoLlegadaDTO tiempollegada)
            throws ExceptionsItinerarioEmpleadoNoEncontrado {

        EntitesTiempoLlegada time = repo.findById(idtiempo).orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Tiempo Llegada no encontrado con ID: " + idtiempo));
        time.setTiempoEstimado(tiempollegada.getTiempoEstimado());
        time.setFechahoraCalculo(tiempollegada.getFechahoraCalculo());


        EntitesTiempoLlegada TiempoLLegada = repo.save(time);
        return convertirATiempoLlegadaDTO(TiempoLLegada);
    }


    public boolean EliminarTiempoLlegada(Long idtiempo) {
        if (!repo.existsById(idtiempo)) return false;
        repo.deleteById(idtiempo);
        return true;
    }


}
