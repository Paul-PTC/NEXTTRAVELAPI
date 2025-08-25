package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.DetallePago;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.DetallePagoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.PromocionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DetallePagoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.DetallePagoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RepositoryPago;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePagoService {

    @Autowired
    private DetallePagoRepository repository;
    @Autowired
    private RepositoryPago repositoryPago;


    public List<DetallePagoDTO> getAllDetallesPago(){
        List<DetallePagoEntities> tipoMantenimieto = repository.findAll();
        return tipoMantenimieto.stream()
                .map(this::convertirADetallepagoDTO)
                .collect(Collectors.toList());
    }

    private DetallePagoDTO convertirADetallepagoDTO(DetallePagoEntities detallespagos) {
        DetallePagoDTO DTO = new DetallePagoDTO();
        DTO.setIdDetallePago(detallespagos.getIdDetallePago());
        DTO.setIdPago(detallespagos.getPago().getIdPago());
        DTO.setMetodo(detallespagos.getMetodo());
        DTO.setReferencia(detallespagos.getReferencia());
        DTO.setObservacion(detallespagos.getObservacion());
        return DTO;
    }

    public DetallePagoDTO insertarDetallePagos(@Valid DetallePagoDTO detalleDTO) {
        try {
            DetallePagoEntities detalleEntit = new DetallePagoEntities();

            // Obtienes la entidad Pago referenciada
            EntitesPago pago = repositoryPago.findById(detalleDTO.getIdPago())
                    .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + detalleDTO.getIdPago()));

            detalleEntit.setPago(pago);  // asignas la entidad padre correctamente
            detalleEntit.setMetodo(detalleDTO.getMetodo());
            detalleEntit.setReferencia(detalleDTO.getReferencia());
            detalleEntit.setObservacion(detalleDTO.getObservacion());

            DetallePagoEntities detalleGuardado = repository.save(detalleEntit);

            return convertirADetallepagoDTO(detalleGuardado);
        } catch (Exception e) {
            e.printStackTrace();
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
