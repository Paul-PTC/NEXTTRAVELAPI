package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.UsuarioServices;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UserEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcpetionUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExcpetionUsuarioNoRegistrdo;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.UserDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UsuarioServices {

    @Autowired
    UserRepository repoUsuario;

    public List obtenerTodo() {
        //Se guarda en una lista de tipo Entity todos los usuarios encontrados en la base
        List<UserEntity> usuario = repoUsuario.findAll();
        return usuario.stream()
                .map(this::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }

    public UserDTO convertirAUsuarioDTO(UserEntity entity){
       UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setIdGrupoExpo(entity.getIdGrupoExpo());
        dto.setIdRol(entity.getIdRol());
        dto.setCorreo(entity.getCorreo());
        dto.setContraseña(entity.getContraseña());
        dto.setIdCargo(entity.getIdCargo());
        return dto;
    }

    public UserDTO insertarDatos(@Valid UserDTO json) {
        //Validacion para verificar si los datos estan correctos
        if (json == null || json.getContraseña() == null || json.getContraseña().isEmpty()){
            throw new IllegalArgumentException("Usuario o contraseña no puede ser nulos");
        }
        try {
            //2 Convertir los datos de tipo DTO a Entity
            UserEntity entity = ConvertiraEmtity(json);
            UserEntity  respuesta = repoUsuario.save(entity);
            return convertirAUsuarioDTO(respuesta);
        }
        catch (Exception e){
            log.error("Error al registrar los usuarios" + e.getMessage());
            throw new ExcpetionUsuarioNoRegistrdo("Error al registrar usuario");
        }
    }

    private UserEntity  ConvertiraEmtity(@Valid UserDTO json) {
        UserEntity  entity = new UserEntity ();
        entity.setNombre(json.getNombre());
        entity.setApellido(json.getApellido());
        entity.setIdGrupoExpo(json.getIdGrupoExpo());
        entity.setIdRol(json.getIdRol());
        entity.setCorreo(json.getCorreo());
        entity.setContraseña(json.getContraseña());
        entity.setIdCargo(json.getIdCargo());
        return entity;
    }

    public UserDTO actualizarUsuario(Long id, @Valid UserDTO json) {
        //1 Verificar la existencia del usuario
        UserEntity  usuarioExistente = repoUsuario.findById(id).orElseThrow(() -> new ExcpetionUsuarioNoEncontrado("Usuario no encontrado"));
        //2. Convertir los datos DTO a Entity
        usuarioExistente = ConvertiraEmtity(json);
        //3. Guardar los cambios (nuevos valores)
        UserEntity  usuarioActualizado = repoUsuario.save(usuarioExistente);
        //4. Convertir los datos a Entity a DTO
        return convertirAUsuarioDTO(usuarioActualizado);
    }
}
