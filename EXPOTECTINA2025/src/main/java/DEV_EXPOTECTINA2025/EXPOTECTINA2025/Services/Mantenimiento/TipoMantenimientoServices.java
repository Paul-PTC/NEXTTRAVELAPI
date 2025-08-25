package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Mantenimiento;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.TipoMantenimietoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsTipoMantenimientoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.TipoMantenimientoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.TipoMantenimientoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoMantenimientoServices {
    @Autowired
    private TipoMantenimientoRespository rep;

    public List<TipoMantenimientoDTO>getAllTipoMantenimiento(){
        List<TipoMantenimietoEntities> tipoMantenimieto = rep.findAll();
        return tipoMantenimieto.stream()
                .map(this::convertirATipoMantenimientosDTO)
                .collect(Collectors.toList());
    }
    public TipoMantenimientoDTO insertarTipoMantenimiento(TipoMantenimientoDTO dto){
        try{
            TipoMantenimietoEntities nuevoTipoMantenimiento = new TipoMantenimietoEntities();

            nuevoTipoMantenimiento.setIdTipoMantenimiento(dto.getIdTipoMantenimiento());
            nuevoTipoMantenimiento.setNombreTipo(dto.getNombreTipo());
            nuevoTipoMantenimiento.setDescripcion(dto.getDescripcion());

            //Guardamos los datos
            rep.save(nuevoTipoMantenimiento);

            return dto;
        }
        catch (Exception e){}
        return null;
    }

    public TipoMantenimientoDTO actualizarTipoMantenimiento(Long id, TipoMantenimientoDTO dto) throws ExceptionsTipoMantenimientoNoEncontrado {
        // Buscar la entidad existente por ID
        TipoMantenimietoEntities existente = rep.findById(id)
                .orElseThrow(() -> new ExceptionsTipoMantenimientoNoEncontrado("No se encontró el tipo de mantenimiento con id: " + id));

        // Actualizar campos (sin modificar ID)
        existente.setNombreTipo(dto.getNombreTipo());
        existente.setDescripcion(dto.getDescripcion());
        // Actualiza otros campos si tienes más en el DTO

        // Guardar cambios
        TipoMantenimietoEntities actualizado = rep.save(existente);

        // Convertir a DTO y devolver
        return convertirA_DTO(actualizado);
    }

    // Método para convertir entidad a DTO
    private TipoMantenimientoDTO convertirA_DTO(TipoMantenimietoEntities entidad) {
        TipoMantenimientoDTO dto = new TipoMantenimientoDTO();
        dto.setIdTipoMantenimiento(entidad.getIdTipoMantenimiento());
        dto.setNombreTipo(entidad.getNombreTipo());
        dto.setDescripcion(entidad.getDescripcion());
        // Mapear otros campos si hay
        return dto;
    }


    public boolean EliminarTipoMantenimineto(Long idTipoMantenimiento){
        try {
            //Validamos la existencia del Tipo de mantenimiento
            TipoMantenimietoEntities objTipoMantenimiento = rep.findById(Long.valueOf(idTipoMantenimiento)).orElse(null);
            //Si existe el objeto se porcede con el proceso de eliminacion
            if (objTipoMantenimiento != null){
                rep.deleteById(Long.valueOf(idTipoMantenimiento));
                return true;
            }else{
                System.out.println("Tipo de mantenimiento no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
         throw new EmptyResultDataAccessException   ("No se encontro usuario con ID :" + idTipoMantenimiento + "Para eliminar", 1);
        }
    }




//Conversion de Entity a DTOOOO
    private TipoMantenimientoDTO convertirATipoMantenimientosDTO(TipoMantenimietoEntities tipoMantenimieto){
        TipoMantenimientoDTO dto = new TipoMantenimientoDTO();
        dto.setIdTipoMantenimiento(tipoMantenimieto.getIdTipoMantenimiento());
        dto.setDescripcion(tipoMantenimieto.getDescripcion());
        dto.setNombreTipo(tipoMantenimieto.getNombreTipo());

        return dto;
    }

    //Conversion de DTO A Entity
    private TipoMantenimietoEntities convertirATipoMantenimientoEntity(TipoMantenimientoDTO dto){
        TipoMantenimietoEntities ent = new TipoMantenimietoEntities();

        ent.setIdTipoMantenimiento(dto.getIdTipoMantenimiento());
        ent.setDescripcion(dto.getDescripcion());
        ent.setNombreTipo(dto.getDescripcion());

        return ent;
    }
}
