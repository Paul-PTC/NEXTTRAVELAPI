package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Controllers.UbicacionEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOUbicacionEmpleado.UbicacionEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UbicacionEmpleado.RepositoryUbicacionEmpleado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.UbicacionEmpleado.UbicacionempleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ApiUbicacion")
public class UbicacionEmpleadoController {
    @Autowired
    private UbicacionempleadoService service;

    @GetMapping("/ObtenerUbicacion")
    public List<UbicacionEmpleadoDTO> ObtenerEmpleados(){return service.ObtenerUbicacionEmpleado();}

}
