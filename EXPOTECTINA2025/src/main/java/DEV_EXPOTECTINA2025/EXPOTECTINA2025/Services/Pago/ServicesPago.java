package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Pago;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ItinerarioEmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOUbicacionempleado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RepositoryPago;
import jakarta.validation.Valid;
import org.hibernate.sql.model.PreparableMutationOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesPago {

    @Autowired
    private RepositoryPago repo;


    public List<DTOPago> getAllPagos() {
        List<EntitesPago> ItiEmp = repo.findAll();
        return ItiEmp.stream()
                .map(this::convertirAPagosEmpleadoDTO)
                .collect(Collectors.toList());
    }

    private DTOPago convertirAPagosEmpleadoDTO(EntitesPago pago) {
        DTOPago dto = new DTOPago();
        dto.setIdPago(pago.getIdPago());
        dto.setIdReserva(pago.getIdReserva());
        dto.setMonto(pago.getMonto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setEstado(pago.getEstado());
        return dto;
    }

    public DTOPago InsertarPagos(DTOPago pago) {
        try {
            EntitesPago Insertpago = new EntitesPago();
            Insertpago.setIdPago(pago.getIdPago());
            Insertpago.setIdReserva(pago.getIdReserva());
            Insertpago.setMonto(pago.getMonto());
            Insertpago.setFechaPago(pago.getFechaPago());
            Insertpago.setEstado(pago.getEstado());

            repo.save(Insertpago);
            return pago;
        } catch (Exception e) {
            return null;
        }
    }


    public DTOPago ActualizarPago(Long idpago, DTOPago dto)
            throws ExceptionsItinerarioEmpleadoNoEncontrado {

        EntitesPago entit = repo.findById(idpago).orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Itinerario no encontrado con ID: " + idpago));

        entit.setMonto(dto.getMonto());
        entit.setFechaPago(dto.getFechaPago());
        entit.setEstado(dto.getEstado());

        EntitesPago pago = repo.save(entit);
        return convertirAPagosEmpleadoDTO(pago);
    }


    public boolean eliminarPago(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }


}