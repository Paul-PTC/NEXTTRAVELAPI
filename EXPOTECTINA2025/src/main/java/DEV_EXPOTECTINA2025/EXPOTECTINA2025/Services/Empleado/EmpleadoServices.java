package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RangoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UserEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RangoRopository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoServices {

    @Autowired
    private EmpleadoRepository repo;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RangoRopository rangoRepository;

    public List<EmpleadoDTO> getAllEmpleados() {
        List<EmpleadoEntities> empleados = repo.findAll();
        return empleados.stream()
                .map(this::convertirAEmpleadoDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoDTO insertarEmpleado(EmpleadoDTO dto) {
        try {
            EmpleadoEntities nuevoEmpleado = new EmpleadoEntities();

            // Asignamos los datos simples
            nuevoEmpleado.setDuiEmpleado(dto.getDuiEmpleado());
            nuevoEmpleado.setNombre(dto.getNombre());
            nuevoEmpleado.setApellido(dto.getApellido());
            nuevoEmpleado.setTelefono(dto.getTelefono());
            nuevoEmpleado.setCorreo(dto.getCorreo());
            nuevoEmpleado.setDireccion(dto.getDireccion());
            nuevoEmpleado.setFotoPerfil(dto.getFotoPerfil());
            nuevoEmpleado.setSalario(dto.getSalario());
            nuevoEmpleado.setEstado(dto.getEstado());

            // Relación con UserEntity
            Optional<UserEntity> usuarioEncontrado = userRepository.findById(dto.getIdUsuario());
            if (usuarioEncontrado.isPresent()) {
                nuevoEmpleado.setUsuario(usuarioEncontrado.get());
            } else {
                return null; // Usuario no encontrado
            }

            // Relación con RangoEntity
            Optional<RangoEntity> rangoEncontrado = rangoRepository.findById(dto.getIdRango());
            if (rangoEncontrado.isPresent()) {
                nuevoEmpleado.setRango(rangoEncontrado.get());
            } else {
                return null; // Rango no encontrado
            }

            // Guardamos
            repo.save(nuevoEmpleado);

            return dto;
        } catch (Exception e) {
            return null;
        }
    }


    public EmpleadoDTO actualizarEmpleados(String dui, EmpleadoDTO empleadoDTO){
        //1. Verificar existencia
        EmpleadoEntities empleadoExistente = repo.findById(dui).orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Usuario no encontrado"));
        //2. Actualizar campos
        //empleadoExistente.setDuiEmpleado(empleadoDTO.getDuiEmpleado()); //vemos talvez error
        //empleadoExistente.setIdUsuario(empleadoDTO.getIdUsuario());
        //empleadoExistente.setIdRango(empleadoDTO.getIdRango());
        empleadoExistente.setNombre(empleadoDTO.getNombre());
        empleadoExistente.setApellido(empleadoDTO.getApellido());
        empleadoExistente.setTelefono(empleadoDTO.getTelefono());
        empleadoExistente.setCorreo(empleadoDTO.getCorreo());
        empleadoExistente.setDireccion(empleadoDTO.getDireccion());
        empleadoExistente.setFotoPerfil(empleadoDTO.getFotoPerfil());
        empleadoExistente.setSalario(empleadoDTO.getSalario());
        empleadoExistente.setEstado(empleadoDTO.getEstado());
        //3. Actualizar relacion con usuario
        if (empleadoDTO.getIdUsuario() != null){
            UserEntity usuario = userRepository.findById(empleadoDTO.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID proporcionado"));
            empleadoExistente.setUsuario(usuario);
        }else {
            empleadoExistente.setUsuario(null);
        }
        //4. Actualizar relacion con Rango
        if (empleadoDTO.getIdRango() != null){
            RangoEntity rango = rangoRepository.findById(empleadoDTO.getIdRango())
                    .orElseThrow(() -> new IllegalArgumentException("Rango no encontrado con ID proporcionado"));
            empleadoExistente.setRango(rango);
        }else {
            empleadoExistente.setRango(null);
        }
        // 5 Guardar cambios
        EmpleadoEntities empleadosActualizar = repo.save(empleadoExistente);
        // 6 Convertir a DTO
        return convertirAEmpleadoDTO(empleadosActualizar);

    }

    public boolean EliminarUsuario(String dui){
        try{
            //Se valida la existencia del usuario previamente a la eliminación
            EmpleadoEntities objUsuario = repo.findById(String.valueOf(dui)).orElse(null);
            //Si objUsuario existe se procede a eliminar
            if (objUsuario != null){
                repo.deleteById(String.valueOf(dui));
                return true;
            }else{
                System.out.println("Usuario no encontrado.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro usuario con ID: " + dui + " para eliminar.", 1);
        }
    }

    // 🔧 Conversión de Entity a DTO
    private EmpleadoDTO convertirAEmpleadoDTO(EmpleadoEntities empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setDuiEmpleado(empleado.getDuiEmpleado());
        dto.setIdUsuario(empleado.getUsuario().getId());
        dto.setIdRango(empleado.getRango().getId());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setTelefono(empleado.getTelefono());
        dto.setCorreo(empleado.getCorreo());
        dto.setDireccion(empleado.getDireccion());
        dto.setFotoPerfil(empleado.getFotoPerfil());
        dto.setSalario(empleado.getSalario());
        dto.setEstado(empleado.getEstado());
        return dto;
    }

}
