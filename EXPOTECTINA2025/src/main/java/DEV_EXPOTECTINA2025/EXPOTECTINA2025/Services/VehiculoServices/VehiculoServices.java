package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.VehiculoServices;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.VehiculosEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionVehiculoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionVehiculoNoRegistrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.VehiculoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.VehiculoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VehiculoServices {
    @Autowired
    private VehiculoRepository repo;

    @Autowired
    private EmpleadoRepository EmpRep;

    @Autowired
    private RutaRepository RutRepo;

    public List<VehiculoDTO>obtenerTodo(){
        //Se guarda en una lista tipo Entity todos los vehiculos encontrados en la base
        List<VehiculosEntities> vehiculos = repo.findAll();
        return vehiculos.stream()
                .map(this::convertirAVehiculoDTO)
                .collect(Collectors.toList());
    }

    public VehiculoDTO convertirAVehiculoDTO(VehiculosEntities ent){
        VehiculoDTO dto = new VehiculoDTO();
        dto.setIdVehiculo(ent.getIdVehiculo());
        dto.setAnio(ent.getAnio());
        dto.setMarca(ent.getMarca());
        dto.setModelo(ent.getModelo());
        dto.setPlaca(ent.getPlaca());
        dto.setFechaVencimientoRevision(ent.getFechaVencimientoRevision());
        dto.setFechaVencimientoCirculacion(ent.getFechaVencimientoCirculacion());
        dto.setFechaVencimientoSeguro(ent.getFechaVencimientoSeguro());


        return dto;
    }

        public VehiculoDTO insertarVehiculo(@Valid VehiculoDTO data) {
            if (data == null || data.getIdVehiculo() == null) {
                throw new IllegalArgumentException("El objeto Vehiculo o su ID no pueden ser nulos");
            }

            try {
                // Convertimos de DTO a entidad base (sin relaciones aún)
                VehiculosEntities nuevoVehiculo = convertirAEntity(data);

                // Buscar y asignar EmpleadoEntities
                EmpleadoEntities empleado = EmpRep.findById(String.valueOf(data.getIdEmpleado()))
                        .orElseThrow(() -> new Exception("Empleado no encontrado con ID: " + data.getIdEmpleado()));
                nuevoVehiculo.setDuiEmpleado(empleado);

                // Buscar y asignar RutaEntities
                RutaEntities ruta = RutRepo.findById(data.getIdRuta())
                        .orElseThrow(() -> new Exception("Ruta no encontrada con ID: " + data.getIdRuta()));
                nuevoVehiculo.setRuta(ruta);

                // Guardar en base de datos
                VehiculosEntities entidadGuardada = repo.save(nuevoVehiculo);

                // Devolver DTO de respuesta
                return convertirAVehiculoDTO(entidadGuardada);

            } catch (Exception e) {
                log.error("Error al registrar el vehículo: " + e.getMessage());
                throw new  ExceptionVehiculoNoRegistrado("Error al registrar vehículo");

            }

        }

    public VehiculosEntities convertirAEntity(@Valid VehiculoDTO json){
        VehiculosEntities ent = new VehiculosEntities();
        ent.setIdVehiculo(json.getIdVehiculo());
        ent.setAnio(json.getAnio());
        ent.setMarca(json.getMarca());
        ent.setModelo(json.getModelo());
        ent.setPlaca(json.getPlaca());
        ent.setFechaVencimientoRevision(json.getFechaVencimientoRevision());
        ent.setFechaVencimientoCirculacion(json.getFechaVencimientoCirculacion());
        ent.setFechaVencimientoSeguro(json.getFechaVencimientoSeguro());
        return ent;

    }

    public VehiculoDTO actualizarVehiculo(Long idVehiculo, @Valid VehiculoDTO json){
        //Verificamos que exista
        //Verificamos que exista el vehículo
        VehiculosEntities vehiculoExiste = repo.findById(idVehiculo)
                .orElseThrow(() -> new ExceptionVehiculoNoEncontrado("Vehículo no encontrado con ID: " + idVehiculo));

        //Convertir campos simples de DTO a Entity
        vehiculoExiste.setIdVehiculo(json.getIdVehiculo());
        vehiculoExiste.setAnio(json.getAnio());
        vehiculoExiste.setMarca(json.getMarca());
        vehiculoExiste.setModelo(json.getModelo());
        vehiculoExiste.setPlaca(json.getPlaca());
        vehiculoExiste.setFechaVencimientoRevision(json.getFechaVencimientoRevision());
        vehiculoExiste.setFechaVencimientoCirculacion(json.getFechaVencimientoCirculacion());
        vehiculoExiste.setFechaVencimientoSeguro(json.getFechaVencimientoSeguro());

        // Actualizar relación con EmpleadoEntities
        EmpleadoEntities empleado = EmpRep.findById(String.valueOf(json.getIdEmpleado()))
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + json.getIdEmpleado()));
        vehiculoExiste.setDuiEmpleado(empleado);

        // Actualizar relación con RutaEntities
        RutaEntities ruta = RutRepo.findById(json.getIdRuta())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + json.getIdRuta()));
        vehiculoExiste.setRuta(ruta);

        //Guardar cambios
        VehiculosEntities vehiculoActualizado = repo.save(vehiculoExiste);


        return convertirAVehiculoDTO(vehiculoActualizado);
    }
    public String eliminarVehiculo(Long idVehiculo) {
        // Verificamos que exista el vehículo
        VehiculosEntities vehiculoExiste = repo.findById(idVehiculo)
                .orElseThrow(() -> new ExceptionVehiculoNoEncontrado("Vehículo no encontrado con ID: " + idVehiculo));

        try {
            // Eliminamos el vehículo
            repo.delete(vehiculoExiste);
            return "Vehículo eliminado correctamente con ID: " + idVehiculo;
        } catch (Exception e) {
            log.error("Error al eliminar el vehículo con ID: " + idVehiculo + " - " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el vehículo");
        }
    }


}
