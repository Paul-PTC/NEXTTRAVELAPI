package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Mantenimiento;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.DetalleMantenimientoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.MantenimientoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RangoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DetalleMantenimientoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.DetalleMantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleMantenimientoServices {
    @Autowired
    private DetalleMantenimientoRepository repo;

    public List<DetalleMantenimientoDTO> getAllDetallesMantenimiento() {
        List<DetalleMantenimientoEntities> detalleMantenimientoEntities  = repo.findAll();
        return detalleMantenimientoEntities.stream()
                .map(this::convertirADetalleDTO)
                .collect(Collectors.toList());
    }

    public DetalleMantenimientoDTO insertarDetalleMantenimiento(DetalleMantenimientoDTO dto){
        try {
            DetalleMantenimientoEntities nuevoDetalle = new DetalleMantenimientoEntities();

            //Ponemos los nuevos datos
            nuevoDetalle.setIdMantenimiento(Math.toIntExact(dto.getIdMantenimiento()));
            nuevoDetalle.setDescripcion(dto.getDescripcion());
            nuevoDetalle.setIdDetalleMantenimiento(Math.toIntExact(dto.getIdMantenimiento()));
            nuevoDetalle.setIdTipoMantenimiento(Math.toIntExact(dto.getIdTipoMantenimiento()));
            //Guardamos
            repo.save(nuevoDetalle);
            return dto;
        }
        catch (Exception e){
            return null;
        }
    }
    public DetalleMantenimientoDTO actualizarDetalle(Long idDetalle, DetalleMantenimientoDTO dto){
        //1. Verificar existencia
       DetalleMantenimientoEntities DetalleExistente = repo.findById(String.valueOf(idDetalle)).orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Rango no encontrado"));
        //2. Actualizar campos
        DetalleExistente.setIdMantenimiento(Math.toIntExact(dto.getIdMantenimiento()));
        DetalleExistente.setDescripcion(dto.getDescripcion());
        DetalleExistente.setIdDetalleMantenimiento(Math.toIntExact(dto.getIdMantenimiento()));
        DetalleExistente.setIdTipoMantenimiento(Math.toIntExact(dto.getIdTipoMantenimiento()));
        // 5 Guardar cambios
        DetalleMantenimientoEntities detalleActualizar = repo.save(DetalleExistente);
        // 6 Convertir a DTO
        return convertirADetalleDTO(detalleActualizar);
    }
    public boolean EliminarDetalleMantenimiento(Long idDetalle){
        try {
        DetalleMantenimientoEntities objDetalle = repo.findById(String.valueOf(idDetalle)).orElse(null);
            if (objDetalle != null) {
                repo.deleteById(String.valueOf(idDetalle));
                return true;
            }
            else {
                System.out.println("Rango no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro usuario con ID: " + idDetalle + " para eliminar.", 1);
        }
    }

    // 🔧 Conversión de Entity a DTO
    private DetalleMantenimientoDTO convertirADetalleDTO(DetalleMantenimientoEntities entities) {
        DetalleMantenimientoDTO dto = new DetalleMantenimientoDTO();
        dto.setIdDetalleMantenimiento(Long.valueOf(entities.getIdDetalleMantenimiento()));
        dto.setIdTipoMantenimiento(Long.valueOf(entities.getIdTipoMantenimiento()));
        dto.setIdDetalleMantenimiento(Long.valueOf(entities.getIdTipoMantenimiento()));
        dto.setDescripcion(entities.getDescripcion());
        return dto;
    }
}
