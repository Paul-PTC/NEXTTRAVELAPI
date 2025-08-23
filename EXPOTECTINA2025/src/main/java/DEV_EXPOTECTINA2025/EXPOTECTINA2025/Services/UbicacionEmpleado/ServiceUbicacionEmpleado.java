package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.UbicacionEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesUbicacionEmpleado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UserEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsUsuarioNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.DTOUbicacionempleado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.EmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RepositoryUbicacionEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceUbicacionEmpleado {

    @Autowired
    private RepositoryUbicacionEmpleado repo;

    public List<DTOUbicacionempleado>   GetAllUbicacions() {
        List<EntitesUbicacionEmpleado> ubicacionEmpleados = repo.findAll();
        return ubicacionEmpleados.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public DTOUbicacionempleado InsertarUbicacionEmpleado(DTOUbicacionempleado ubicacion) {
        try{
            EntitesUbicacionEmpleado entitie = new EntitesUbicacionEmpleado();

            //Asignamos los datos simples.
            entitie.setIdUbicacionEmpleado(ubicacion.getIdUbicacionEmpleado());
            entitie.setDuiEmpleado(ubicacion.getDuiEmpleado());
            entitie.setUbicacionEmpleado(ubicacion.getUbicacionEmpleado());
            entitie.setFechaRegistro(ubicacion.getFechaRegistro());

            Optional<EntitesUbicacionEmpleado> DUIencontrado = repo.findByDui(String.valueOf(ubicacion.getDuiEmpleado()));
            if (DUIencontrado.isPresent()){
                entitie.setDuiEmpleado(DUIencontrado.get().getDuiEmpleado());
            }else{
                return null; //DUI no encontrado
            }

            repo.save(entitie);

            return ubicacion;
        }catch (Exception e){
            return null;
        }
    }

    public DTOUbicacionempleado actualizarEmpleadoUbicaacion(Long id, DTOUbicacionempleado ubicacion){
        EntitesUbicacionEmpleado entites = repo.findById(id).orElseThrow(() -> new ExceptionsUsuarioNoEncontrado("Ubicacion no encontrado"));
        //El DUI solo podria actualizarlo desde el Empleado de lo demas no podra
        //entites.setDuiEmpleado(ubicacion.getDuiEmpleado());
        entites.setUbicacionEmpleado(ubicacion.getUbicacionEmpleado());
        entites.setFechaRegistro(ubicacion.getFechaRegistro());

        EntitesUbicacionEmpleado actualizar = repo.save(entites);
        return ConvertirADTO(entites);
    }


    public boolean EliminarUbicacion(Long id) {
        try{
            //Se valida la existencia del usuario previamente a la eliminación
            EntitesUbicacionEmpleado objUsuario = repo.findById(id).orElse(null);
            //Si objUsuario existe se procede a eliminar
            if (objUsuario != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Usuario no encontrado.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro usuario con ID: " + id + " para eliminar.", 1);
        }
    }


    private DTOUbicacionempleado ConvertirADTO(EntitesUbicacionEmpleado ubicacion){
        DTOUbicacionempleado ubi = new DTOUbicacionempleado();
        ubi.setIdUbicacionEmpleado(ubicacion.getIdUbicacionEmpleado());
        ubi.setDuiEmpleado(ubicacion.getDuiEmpleado());
        ubi.setUbicacionEmpleado(ubicacion.getUbicacionEmpleado());
        ubi.setUbicacionEmpleado(ubicacion.getUbicacionEmpleado());
        return ubi;
    }
}
