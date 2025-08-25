package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.DescuentoAplicado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.*;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DescuentoAplicadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.DescuentoAplicadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.PromocionRepo;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ReservaRepository;
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
   @Autowired
   private ReservaRepository reservaRepository;

   @Autowired
   private PromocionRepo promocionRepository;

    public List<DescuentoAplicadoDTO> getAllDescuento(){
        List<DescuentoAplicadoEntities> empleados = repo.findAll();
        return empleados.stream()
                .map(this::convertirADescuentoAplicadoDTO)
                .collect(Collectors.toList());
    }

    private DescuentoAplicadoDTO convertirADescuentoAplicadoDTO(DescuentoAplicadoEntities descuentoAplicadoEntities) {
        DescuentoAplicadoDTO dto = new DescuentoAplicadoDTO();
        dto.setIdDescuento(descuentoAplicadoEntities.getIdDescuento());
        dto.setIdReserva(descuentoAplicadoEntities.getReserva().getId());

        // Obtener la promoción directamente de la entidad, no del DTO
        PromocionEntities promocion = descuentoAplicadoEntities.getPromocion();
        if (promocion == null) {
            throw new RuntimeException("Promoción no encontrada en la entidad");
        }
        dto.setIdPromocion(promocion.getIdPromocion());  // Aquí asignas el idPromocion en el DTO

        dto.setMontoDescontado(descuentoAplicadoEntities.getMontoDescontado());
        dto.setFechaAplicacion(descuentoAplicadoEntities.getFechaAplicacion());

        return dto;
    }

    public DescuentoAplicadoDTO insertarDescuentoAplicado(@Valid DescuentoAplicadoDTO dto) {
        try {
            if (dto.getIdReserva() == null) {
                throw new IllegalArgumentException("El idReserva no puede ser null");
            }

            if (dto.getIdPromocion() == null) {
                throw new IllegalArgumentException("El idPromocion no puede ser null");
            }

            System.out.println("idReserva recibido: " + dto.getIdReserva());
            System.out.println("idPromocion recibido: " + dto.getIdPromocion());

            DescuentoAplicadoEntities descuentoAplicado = new DescuentoAplicadoEntities();

            descuentoAplicado.setMontoDescontado(dto.getMontoDescontado());
            descuentoAplicado.setFechaAplicacion(dto.getFechaAplicacion());

            ReservaEntities reserva = reservaRepository.findById(dto.getIdReserva())
                    .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + dto.getIdReserva()));

            PromocionEntities promocion = promocionRepository.findById(dto.getIdPromocion())
                    .orElseThrow(() -> new RuntimeException("Promoción no encontrada con ID: " + dto.getIdPromocion()));

            descuentoAplicado.setReserva(reserva);
            descuentoAplicado.setPromocion(promocion);

            DescuentoAplicadoEntities guardado = repo.save(descuentoAplicado);

            dto.setIdDescuento(guardado.getIdDescuento());
            return dto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DescuentoAplicadoDTO actualizarDescuentoAplicado(Long idDescuento, @Valid DescuentoAplicadoDTO descuentoDTO) {
        if (idDescuento == null) {
            throw new IllegalArgumentException("El idDescuento no puede ser null");
        }
        if (descuentoDTO.getIdReserva() == null) {
            throw new IllegalArgumentException("El idReserva no puede ser null");
        }
        if (descuentoDTO.getIdPromocion() == null) {
            throw new IllegalArgumentException("El idPromocion no puede ser null");
        }

        DescuentoAplicadoEntities entit = repo.findById(idDescuento)
                .orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Descuento no encontrado con ID: " + idDescuento));

        entit.setMontoDescontado(descuentoDTO.getMontoDescontado());
        entit.setFechaAplicacion(descuentoDTO.getFechaAplicacion());

        ReservaEntities reserva = reservaRepository.findById(descuentoDTO.getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + descuentoDTO.getIdReserva()));
        entit.setReserva(reserva);

        PromocionEntities promocion = promocionRepository.findById(descuentoDTO.getIdPromocion())
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada con ID: " + descuentoDTO.getIdPromocion()));
        entit.setPromocion(promocion);

        DescuentoAplicadoEntities entitesDescuento = repo.save(entit);
        return convertirADescuentoAplicadoDTO(entitesDescuento);
    }

    public boolean EliminarDescuentosAplicados(Long id){
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
