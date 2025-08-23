package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.DetallePago;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.DetallePagoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.PromocionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DetallePagoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.DetallePagoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePagoService {

    @Autowired
    private DetallePagoRepository repository;


    public List<DetallePagoDTO> getAllDetallesPago(){
        List<DetallePagoEntities> tipoMantenimieto = repository.findAll();
        return tipoMantenimieto.stream()
                .map(this::convertirADetallepagoDTO)
                .collect(Collectors.toList());
    }

    private DetallePagoDTO convertirADetallepagoDTO(DetallePagoEntities detallespagos) {
        DetallePagoDTO DTO = new DetallePagoDTO();
        DTO.setIdDetallePago(detallespagos.getIdDetallePago());
        DTO.setIdPago(detallespagos.getIdPago());
        DTO.setMetodo(detallespagos.getMetodo());
        DTO.setReferencia(detallespagos.getReferencia());
        DTO.setObservacion(detallespagos.getObservacion());
        return DTO;
    }

    public DetallePagoDTO InsertarDetallePagos(@Valid DetallePagoDTO detalleDTO) {
        try {
            DetallePagoEntities detalleEntit = new DetallePagoEntities();

            detalleEntit.setMetodo(detalleDTO.getMetodo());
            detalleEntit.setReferencia(detalleDTO.getReferencia());
            detalleEntit.setObservacion(detalleDTO.getObservacion());

            return detalleDTO;
        } catch (Exception e) {
            return null;
        }
    }


    public DetallePagoDTO actualizarDetallePago(Long idDetallePago, @Valid DetallePagoDTO detalleDTO) {
        try{
            DetallePagoEntities entit = repository.findById(idDetallePago).orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Detalle Pago no encontrado con ID: " + idDetallePago));

            entit.setMetodo(detalleDTO.getMetodo());
            entit.setReferencia(detalleDTO.getReferencia());
            entit.setObservacion(detalleDTO.getObservacion());

            DetallePagoEntities detallePagoEntities = repository.save(entit);
            return convertirADetallepagoDTO(detallePagoEntities);
        }catch (Exception e) {
            return null;
        }
    }

    public boolean EliminarDetallesPagos(Long idDetllePago) {
        if (!repository.existsById(idDetllePago)) return false;
        repository.deleteById(idDetllePago);
        return true;
    }


}
