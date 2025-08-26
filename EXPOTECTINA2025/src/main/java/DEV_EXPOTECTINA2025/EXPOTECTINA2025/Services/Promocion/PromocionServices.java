package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Promocion;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.PromocionEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPago;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOPromocion;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.PromocionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromocionServices {

    private final PromocionRepo promoRepo;

    // listar
    public List<DTOPromocion> listar() {
        return promoRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // obtener por id
    public Optional<DTOPromocion> obtenerPorId(Long id) {
        return promoRepo.findById(id).map(this::toDTO);
    }

    // crear
    @Transactional
    public DTOPromocion crear(DTOPromocion dto) {
        PromocionEntities e = new PromocionEntities();
        applyDtoToEntity(dto, e);
        return toDTO(promoRepo.save(e));
    }

    // actualizar
    @Transactional
    public Optional<DTOPromocion> actualizar(Long id, DTOPromocion dto) {
        Optional<PromocionEntities> opt = promoRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        PromocionEntities e = opt.get();
        applyDtoToEntity(dto, e);
        return Optional.of(toDTO(promoRepo.save(e)));
    }

    // eliminar
    @Transactional
    public boolean eliminar(Long id) {
        if (!promoRepo.existsById(id)) return false;
        promoRepo.deleteById(id);
        return true;
    }

    // mapper entity -> dto
    private DTOPromocion toDTO(PromocionEntities e) {
        DTOPromocion d = new DTOPromocion();
        d.setIdPromocion(e.getIdPromocion());
        d.setNombre(e.getNombre());
        d.setDescripcion(e.getDescripcion());
        d.setPorcentaje(e.getPorcentaje());
        d.setFechaInicio(e.getFechaInicio());
        d.setFechaFin(e.getFechaFin());
        d.setPuntosRequeridos(e.getPuntosRequeridos());
        d.setEstado(e.getEstado());
        return d;
    }

    // aplicar dto -> entity
    private void applyDtoToEntity(DTOPromocion d, PromocionEntities e) {
        e.setNombre(d.getNombre());
        e.setDescripcion(d.getDescripcion());
        e.setPorcentaje(d.getPorcentaje());
        e.setFechaInicio(d.getFechaInicio());
        e.setFechaFin(d.getFechaFin());
        e.setPuntosRequeridos(d.getPuntosRequeridos());
        e.setEstado(d.getEstado());
    }
}