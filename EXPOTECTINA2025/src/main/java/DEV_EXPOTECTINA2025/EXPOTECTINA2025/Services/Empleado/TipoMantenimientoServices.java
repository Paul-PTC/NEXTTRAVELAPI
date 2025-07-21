package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado;

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

    public TipoMantenimientoDTO actualizarTipoMantenimientos (Long idTipoMantenimiento, TipoMantenimientoDTO tipoMantenimientoDTO){
        //Verificamos existencia
        TipoMantenimietoEntities TipoManteniminetoExiste = rep.findById(Long.valueOf(idTipoMantenimiento)).orElseThrow(()-> new ExceptionsTipoMantenimientoNoEncontrado("Tipo de mantenimiento no encontrado"));

        TipoManteniminetoExiste.setIdTipoMantenimiento(tipoMantenimientoDTO.getIdTipoMantenimiento());
        TipoManteniminetoExiste.setDescripcion(tipoMantenimientoDTO.getDescripcion());
        TipoManteniminetoExiste.setNombreTipo(tipoMantenimientoDTO.getNombreTipo());

        //Guardamos Cambios
        TipoMantenimietoEntities TipoMantenimientoActualizar = rep.save(TipoManteniminetoExiste);

        //Convertimos a DTO
        return convertirATipoMantenimientosDTO(TipoMantenimientoActualizar);
    }

    public TipoMantenimientoDTO actualizarTipoMantenimiento(Long idTipoMantenimiento, TipoMantenimientoDTO tipoMantenimientoDTO) {
        // Verificamos existencia
        TipoMantenimietoEntities tipoMantenimientoExiste = rep.findById(idTipoMantenimiento)
                .orElseThrow(() -> new ExceptionsTipoMantenimientoNoEncontrado("Tipo de mantenimiento no encontrado con ID: " + idTipoMantenimiento));

        // Solo actualiza los campos que vienen no nulos en el DTO
        if (tipoMantenimientoDTO.getIdTipoMantenimiento() != null) {
            tipoMantenimientoExiste.setIdTipoMantenimiento(tipoMantenimientoDTO.getIdTipoMantenimiento());
        }

        if (tipoMantenimientoDTO.getDescripcion() != null) {
            tipoMantenimientoExiste.setDescripcion(tipoMantenimientoDTO.getDescripcion());
        }

        if (tipoMantenimientoDTO.getNombreTipo() != null) {
            tipoMantenimientoExiste.setNombreTipo(tipoMantenimientoDTO.getNombreTipo());
        }

        // Guardamos los cambios
        TipoMantenimietoEntities tipoMantenimientoActualizado = rep.save(tipoMantenimientoExiste);

        // Convertimos a DTO
        return convertirATipoMantenimientosDTO(tipoMantenimientoActualizado);
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
