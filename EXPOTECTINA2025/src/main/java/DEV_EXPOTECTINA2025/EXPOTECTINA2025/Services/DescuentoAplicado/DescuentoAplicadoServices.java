package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.DescuentoAplicado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.*;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DescuentoAplicadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.DescuentoAplicadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DescuentoAplicadoServices {
    @Autowired
    private DescuentoAplicadoRepository repo;

    public List<DescuentoAplicadoDTO> getAllDescuento(){
        List<DescuentoAplicadoEntities> empleados = repo.findAll();
        return empleados.stream()
                .map(this::convertirADescuentoAplicadoDTO)
                .collect(Collectors.toList());
    }

    private DescuentoAplicadoDTO convertirADescuentoAplicadoDTO(DescuentoAplicadoEntities descuentoAplicadoEntities) {
        DescuentoAplicadoDTO dto = new DescuentoAplicadoDTO();
        dto.setIdDescuento(descuentoAplicadoEntities.getIdDescuento());
        dto.setIdReserva(descuentoAplicadoEntities.getIdReserva());
        dto.setIdPromocion(descuentoAplicadoEntities.getIdPromocion());
        dto.setMontoDescontado(descuentoAplicadoEntities.getMontoDescontado());
        dto.setFechaAplicacion(descuentoAplicadoEntities.getFechaAplicacion());

        return dto;
    }

    public DescuentoAplicadoDTO InsertarDescuentoAplicado(@Valid DescuentoAplicadoDTO descuento){
        try {
            DescuentoAplicadoEntities descuentoaplicado = new DescuentoAplicadoEntities();

            descuentoaplicado.setMontoDescontado(descuento.getMontoDescontado());
            descuentoaplicado.setFechaAplicacion(descuento.getFechaAplicacion());

            // Guardamos
            repo.save(descuentoaplicado);

            return descuento;
        } catch (Exception e) {
            return null;
        }
    }

    public DescuentoAplicadoDTO actualizarEmpleados(Long idDescuento, @Valid DescuentoAplicadoDTO descuentoDTO){
        DescuentoAplicadoEntities entit = repo.findById(idDescuento).orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Descuento no encontrado con ID: " + idDescuento));

        entit.setMontoDescontado(descuentoDTO.getMontoDescontado());
        entit.setFechaAplicacion(Date.valueOf(descuentoDTO.getMontoDescontado()));

        DescuentoAplicadoEntities entitesDescuento = repo.save(entit);
        return convertirADescuentoAplicadoDTO(entitesDescuento);
    }

    public boolean EliminarDescuentosAplicados(Long id){
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
