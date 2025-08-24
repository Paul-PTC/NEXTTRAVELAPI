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
        List<DetalleMantenimientoEntities> detalleMantenimientoEntities = repo.findAll();
        return detalleMantenimientoEntities.stream()
                .map(this::convertirADetalleDTO)
                .collect(Collectors.toList());
    }

    public DetalleMantenimientoDTO insertarDetalleMantenimiento(DetalleMantenimientoDTO dto) {
        long start = System.currentTimeMillis();
        try {
            DetalleMantenimientoEntities nuevoDetalle = new DetalleMantenimientoEntities();
            nuevoDetalle.setIdMantenimiento(Math.toIntExact(dto.getIdMantenimiento()));
            nuevoDetalle.setDescripcion(dto.getDescripcion());
            nuevoDetalle.setIdTipoMantenimiento(Math.toIntExact(dto.getIdTipoMantenimiento()));

            repo.save(nuevoDetalle);

            long end = System.currentTimeMillis();
            System.out.println("insertarDetalleMantenimiento duró: " + (end - start) + " ms");

            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DetalleMantenimientoDTO actualizarDetalle(Long idDetalle, DetalleMantenimientoDTO dto) {
        try {
            // Busca por id numérico, no por String
            DetalleMantenimientoEntities detalleExistente = repo.findById(String.valueOf(idDetalle.intValue()))
                    .orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Detalle no encontrado"));

            // Actualiza solo campos modificables (NO el ID)
            detalleExistente.setIdMantenimiento(dto.getIdMantenimiento().intValue());
            detalleExistente.setDescripcion(dto.getDescripcion());
            detalleExistente.setIdTipoMantenimiento(dto.getIdTipoMantenimiento().intValue());

            DetalleMantenimientoEntities detalleActualizado = repo.save(detalleExistente);
            return convertirADetalleDTO(detalleActualizado);

        } catch (Exception e) {
            e.printStackTrace(); // Para loguear cualquier error
            throw e; // Puedes propagar la excepción o manejarla como prefieras
        }
    }

    public boolean EliminarDetalleMantenimiento(Long idDetalle) {
        try {
            DetalleMantenimientoEntities objDetalle = repo.findById(String.valueOf(idDetalle.intValue())).orElse(null);
            if (objDetalle != null) {
                repo.deleteById(String.valueOf(idDetalle.intValue()));
                return true;
            } else {
                System.out.println("Detalle no encontrado");
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new EmptyResultDataAccessException("No se encontro detalle con ID: " + idDetalle + " para eliminar.", 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 🔧 Conversión de Entity a DTO
    private DetalleMantenimientoDTO convertirADetalleDTO(DetalleMantenimientoEntities entities) {
        DetalleMantenimientoDTO dto = new DetalleMantenimientoDTO();
        dto.setIdDetalleMantenimiento(Long.valueOf(entities.getIdDetalleMantenimiento()));
        dto.setIdTipoMantenimiento(Long.valueOf(entities.getIdTipoMantenimiento()));
        dto.setIdMantenimiento(Long.valueOf(entities.getIdMantenimiento())); // corregido
        dto.setDescripcion(entities.getDescripcion());
        return dto;
    }
}
