package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.UbicacionEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UbicacionEmpleado.EntityUbicacionEmpleado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOUbicacionEmpleado.UbicacionEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UbicacionEmpleado.RepositoryUbicacionEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UbicacionempleadoService {

    @Autowired
    private RepositoryUbicacionEmpleado repo;

    public List<UbicacionEmpleadoDTO> ObtenerUbicacionEmpleado() {
        List<EntityUbicacionEmpleado> ubicaion = repo.findAll();
        return ubicaion.stream()
                .map(this::ConvertirAUbicacionDTO)
                .collect(Collectors.toList());
    }
    //Conversion de entity a DTO
     public UbicacionEmpleadoDTO ConvertirAUbicacionDTO(EntityUbicacionEmpleado entity) {
        UbicacionEmpleadoDTO dto = new UbicacionEmpleadoDTO();
        dto.setIdBitacora(entity.getIdBitacora());
        dto.setDuiEmpleado(entity.getDuiEmpleado());
        dto.setUbicacion(entity.getUbicacion());
        dto.setFecharegistro(entity.getFecharegistro());
        return dto;
    }
}
