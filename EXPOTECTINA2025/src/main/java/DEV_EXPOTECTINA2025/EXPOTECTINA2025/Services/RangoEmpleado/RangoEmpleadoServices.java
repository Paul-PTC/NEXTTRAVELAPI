package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.RangoEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RangoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RangoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RangoRopository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RangoEmpleadoServices {
    @Autowired
    private RangoRopository rangoRepository;

    public List<RangoDTO> getAllRangoEmpleado() {
        List<RangoEntity> rangoEmpleados = rangoRepository.findAll();
        return rangoEmpleados.stream()
                .map(this::convertirARangoDTO)
                .collect(Collectors.toList());
    }

    public RangoDTO insertarRangoEmpleado(RangoDTO dto){
        try {
            RangoEntity nuevoRangoEmpleado = new RangoEntity();

            //Ponemos los nuevos datos
            nuevoRangoEmpleado.setNombreRango(dto.getNombreRango());
            nuevoRangoEmpleado.setDescripcion(dto.getDescripcion());

            //Guardamos
            rangoRepository.save(nuevoRangoEmpleado);
            return dto;
        }
        catch (Exception e){
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
        dto.setNombreRango(dto.getNombreRango());
        dto.setNombreRango(dto.getDescripcion());
        return dto;
    }
}
