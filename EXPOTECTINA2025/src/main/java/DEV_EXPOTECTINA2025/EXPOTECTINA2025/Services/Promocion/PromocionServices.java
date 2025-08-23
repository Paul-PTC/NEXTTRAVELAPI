package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Promocion;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.PromocionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPromocion;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.PromocionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromocionServices {

    @Autowired
    private PromocionRepo repo;

    public List<DTOPromocion> getAllPromocion() {
        List<PromocionEntities> promo = repo.findAll();
        return promo.stream()
                .map(this::convertirAPromocionEmpleadoDTO)
                .collect(Collectors.toList());
    }

    private DTOPromocion convertirAPromocionEmpleadoDTO(PromocionEntities promo) {
        DTOPromocion dto = new DTOPromocion();
        promo.setIdPromocion(dto.getIdPromocion());
        dto.setNombre(promo.getNombre());
        dto.setDescripcion(promo.getDescripcion());
        dto.setPorcentaje(Float.parseFloat(promo.getDescripcion()));
        dto.setFechainicio((Date) promo.getFechainicio());
        dto.setFechafin((Date) promo.getFechafin());
        dto.setPuntosRequeridos(promo.getPuntosRequeridos());
        dto.setEstado(promo.getEstado());

        return dto;
    }

    public DTOPromocion InsertarPromocion(DTOPromocion pago) {
        try {
            PromocionEntities promoEnti = new PromocionEntities();
            promoEnti.setNombre(pago.getNombre());
            promoEnti.setDescripcion(pago.getDescripcion());
            promoEnti.setPorcentaje(pago.getPorcentaje());
            promoEnti.setFechainicio(pago.getFechainicio());
            promoEnti.setFechafin(pago.getFechafin());
            promoEnti.setPuntosRequeridos(pago.getPuntosRequeridos());
            promoEnti.setEstado(pago.getEstado());
            return pago;
        } catch (Exception e) {
            return null;
        }
    }


    public DTOPromocion ActualizarPromocion(Long idpromo, DTOPromocion dto){
        try{

            PromocionEntities entit = repo.findById(idpromo).orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Promocion no encontrado con ID: " + idpromo));
            entit.setNombre(dto.getNombre());
            entit.setDescripcion(dto.getDescripcion());
            entit.setPorcentaje(dto.getPorcentaje());
            entit.setFechainicio(dto.getFechainicio());
            entit.setFechafin(dto.getFechafin());
            entit.setPuntosRequeridos(dto.getPuntosRequeridos());
            entit.setEstado(dto.getEstado());

            PromocionEntities pago = repo.save(entit);
            return convertirAPromocionEmpleadoDTO(pago);
        }catch (Exception e) {
            return null;
        }
    }



    public boolean eliminarPromocion(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

}
