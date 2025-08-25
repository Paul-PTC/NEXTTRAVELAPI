package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Pago;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.*;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOUbicacionempleado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RepositoryPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Autowired
    private ReservaRepository repoReserva;


    public List<DTOPago> getAllPagos() {
        List<EntitesPago> ItiEmp = repo.findAll();
        return ItiEmp.stream()
                .map(this::convertirAPagosEmpleadoDTO)
                .collect(Collectors.toList());
    }

    private DTOPago convertirAPagosEmpleadoDTO(EntitesPago pago) {
        DTOPago dto = new DTOPago();
        dto.setIdPago(pago.getIdPago());
        dto.setIdReserva(pago.getReserva().getId());
        dto.setMonto(pago.getMonto());
        dto.setFechaPago(pago.getFechaPago());
        dto.setEstado(pago.getEstado());
        return dto;
    }

    public DTOPago InsertarPagos(DTOPago pago) {
        EntitesPago insertPago = new EntitesPago();
        insertPago.setIdPago(pago.getIdPago());

        // convertir id -> entidad
        ReservaEntities reserva = repoReserva.findById(pago.getIdReserva())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe Reserva con ID: " + pago.getIdReserva()));

        insertPago.setReserva(reserva);
        insertPago.setMonto(pago.getMonto());
        insertPago.setFechaPago(pago.getFechaPago());
        insertPago.setEstado(pago.getEstado());
        repo.save(insertPago);
        return pago; // o mapea a DTO de salida
    }


    public DTOPago ActualizarPago(Long idpago, DTOPago dto){

        EntitesPago entit = repo.findById(idpago).orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Pago no encontrado con ID: " + idpago));

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