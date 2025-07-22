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


    public List<VehiculoDTO> obtenerTodo() {
        //Se guarda en una lista tipo Entity todos los vehiculos encontrados en la base
        List<VehiculosEntities> vehiculos = repo.findAll();
        return vehiculos.stream()
                .map(this::convertirAVehiculoDTO)
                .collect(Collectors.toList());
    }

    public VehiculoDTO convertirAVehiculoDTO(VehiculosEntities ent) {
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

    public VehiculoDTO insertarVehiculo(@Valid VehiculoDTO data) throws Exception {
        if (data == null) {
            throw new IllegalArgumentException("El objeto Vehiculo no puede ser nulo");
        }

        // Validar que no exista otro vehículo con la misma placa
        if (repo.existsByPlaca(data.getPlaca())) {
            throw new Exception("Ya existe un vehículo con esta placa");
        }

        // Convertir DTO a Entity
        VehiculosEntities entidad = convertirAEntity(data);

        // Guardar entidad (ID será generado automáticamente por la secuencia)
        VehiculosEntities vehiculoGuardado = repo.save(entidad);

        // Convertir a DTO y devolver
        return convertirAVehiculoDTO(vehiculoGuardado);
    }


    public VehiculosEntities convertirAEntity(@Valid VehiculoDTO json) {
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

    public VehiculoDTO actualizarVehiculo(Long idVehiculo, @Valid VehiculoDTO json) {
        VehiculosEntities vehiculoExiste = repo.findById(idVehiculo)
                .orElseThrow(() -> new ExceptionVehiculoNoEncontrado("Vehículo no encontrado con ID: " + idVehiculo));

        vehiculoExiste.setAnio(json.getAnio());
        vehiculoExiste.setMarca(json.getMarca());
        vehiculoExiste.setModelo(json.getModelo());
        vehiculoExiste.setPlaca(json.getPlaca());
        vehiculoExiste.setFechaVencimientoRevision(json.getFechaVencimientoRevision());
        vehiculoExiste.setFechaVencimientoCirculacion(json.getFechaVencimientoCirculacion());
        vehiculoExiste.setFechaVencimientoSeguro(json.getFechaVencimientoSeguro());

        VehiculosEntities actualizado = repo.save(vehiculoExiste);

        return convertirAVehiculoDTO(actualizado);
    }

    public String eliminarVehiculo(Long idVehiculo) {
        VehiculosEntities vehiculoExiste = repo.findById(idVehiculo)
                .orElseThrow(() -> new ExceptionVehiculoNoEncontrado("Vehículo no encontrado con ID: " + idVehiculo));
        try {
            repo.delete(vehiculoExiste);
            return "Vehículo eliminado correctamente con ID: " + idVehiculo;
        } catch (Exception e) {
            log.error("Error al eliminar el vehículo con ID: " + idVehiculo + " - " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el vehículo");
        }
    }

}

