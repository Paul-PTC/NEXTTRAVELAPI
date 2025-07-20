package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.LugarTuristico;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.LugarTuristicoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RangoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.LugarTuristicoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.LugarTuristicoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RangoRopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LugarTuristicoServices {
    @Autowired
    private LugarTuristicoRepository lugarRepository;

    public List<LugarTuristicoDTO> getAllLugarTuristico() {
        List<LugarTuristicoEntity> lugarTuristico = lugarRepository.findAll();
        return lugarTuristico.stream()
                .map(this::convertirALugarTuristicoDTO)
                .collect(Collectors.toList());
    }

    public LugarTuristicoDTO insertarLugarTuristico(LugarTuristicoDTO dto) {
        try {
            LugarTuristicoEntity nuevoLugarTuristico = new LugarTuristicoEntity();

            // Asignar los datos del DTO a la entidad
            nuevoLugarTuristico.setNombre(dto.getNombre());
            nuevoLugarTuristico.setTipoLugar(dto.getTipoLugar());
            nuevoLugarTuristico.setDescripcion(dto.getDescripcion());
            nuevoLugarTuristico.setLatitud(dto.getLatitud());
            nuevoLugarTuristico.setLongitud(dto.getLongitud());

            // Guardar en la base de datos
            lugarRepository.save(nuevoLugarTuristico);

            // Retornar el DTO (opcionalmente puedes convertir desde la entidad)
            return dto;
        } catch (Exception e) {
            e.printStackTrace(); // para ver el error en consola
            return null;
        }
    }


    public LugarTuristicoDTO actualizarLugarTuristico(Long idLugar, LugarTuristicoDTO dto) {
        // 1. Verificar existencia del lugar turístico
        LugarTuristicoEntity lugarExistente = lugarRepository.findById(idLugar)
                .orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Lugar no encontrado"));

        // 2. Actualizar campos
        lugarExistente.setNombre(dto.getNombre());
        lugarExistente.setTipoLugar(dto.getTipoLugar());
        lugarExistente.setDescripcion(dto.getDescripcion());
        lugarExistente.setLatitud(dto.getLatitud());
        lugarExistente.setLongitud(dto.getLongitud());

        // 3. Guardar cambios
        LugarTuristicoEntity lugarActualizado = lugarRepository.save(lugarExistente);

        // 4. Convertir a DTO
        return convertirALugarTuristicoDTO(lugarActualizado);
    }


    // 🔧 Conversión de Entity a DTO
    private LugarTuristicoDTO convertirALugarTuristicoDTO(LugarTuristicoEntity lugarTuristico) {
        LugarTuristicoDTO dto = new LugarTuristicoDTO();
        dto.setIdLugar(lugarTuristico.getIdLugar());
        dto.setNombre(lugarTuristico.getNombre());
        dto.setTipoLugar(lugarTuristico.getTipoLugar());
        dto.setDescripcion(lugarTuristico.getDescripcion());
        dto.setLatitud(lugarTuristico.getLatitud());
        dto.setLongitud(lugarTuristico.getLongitud());
        return dto;
    }


    public boolean EliminarLugarTuristico(Long idLugar){
        try {
            LugarTuristicoEntity objLugarTuristico = lugarRepository.findById(idLugar).orElse(null);
            if (objLugarTuristico != null) {
                lugarRepository.deleteById(idLugar);
                return true;
            }
            else {
                System.out.println("Lugar no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro lugar con ID: " + idLugar + " para eliminar.", 1);
        }
    }
}
