package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.RangoEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RangoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RangoRopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RangoEmpleadoServices {
    @Autowired
    private RangoRopository rangoRepository;

    public List<RangoDTO> getAllRangoEmpleado() {
        List<RangoEntity> rangoEmpleados = rangoRepository.findAll();
        return rangoEmpleados.stream()
                .map(this::convertirARangoDTO)
                .collect(Collectors.toList());
    }

    public RangoDTO insertarRangoEmpleado(RangoDTO dto) {
        try {
            RangoEntity nuevoRangoEmpleado = new RangoEntity();

            nuevoRangoEmpleado.setNombreRango(dto.getNombreRango());
            nuevoRangoEmpleado.setDescripcion(dto.getDescripcion());

            rangoRepository.save(nuevoRangoEmpleado);

            return dto;
        } catch (Exception e) {
            e.printStackTrace(); // ¡Para que puedas ver el error!
            return null;
        }
    }
    public RangoDTO actualizarRangoEmpleado(Long id, RangoDTO dto){
        //1. Verificar existencia
        RangoEntity rangoExistente = rangoRepository.findById(id).orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Rango no encontrado"));
        //2. Actualizar campos
        rangoExistente.setId(dto.getId()); //vemos talvez error
        rangoExistente.setNombreRango(dto.getNombreRango());
        rangoExistente.setDescripcion(dto.getDescripcion());

        // 5 Guardar cambios
        RangoEntity rangoActualizar = rangoRepository.save(rangoExistente);
        // 6 Convertir a DTO
        return convertirARangoDTO(rangoActualizar);
    }

    // 🔧 Conversión de Entity a DTO
    private RangoDTO convertirARangoDTO(RangoEntity rango) {
        RangoDTO dto = new RangoDTO();
        dto.setId(rango.getId());
        dto.setNombreRango(rango.getNombreRango());
        dto.setDescripcion(rango.getDescripcion());
        return dto;
    }

    public boolean EliminarEmpleadoRango(Long id){
        try {
            RangoEntity objRango = rangoRepository.findById(id).orElse(null);
            if (objRango != null) {
                rangoRepository.deleteById(id);
                return true;
            }
            else {
                System.out.println("Rango no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro usuario con ID: " + id + " para eliminar.", 1);
        }
    }
}
