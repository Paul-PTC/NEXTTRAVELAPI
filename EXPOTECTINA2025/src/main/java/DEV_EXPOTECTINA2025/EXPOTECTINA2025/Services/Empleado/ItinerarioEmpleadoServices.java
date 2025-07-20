package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ItinerarioEmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ItinerarioEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItinerarioEmpleadoServices {

    @Autowired
    private ItinerarioEmpleadoRepository rep;

    public List<ItinerarioEmpleadoDTO>getALLItinerarioEmpleado(){
        List<ItinerarioEmpleadoEntities> ItiEmp = rep.findAll();
        return ItiEmp.stream()
                .map(this::convertirAItinerarioEmpleadoDTO)
                .collect(Collectors.toList());
    }

   public ItinerarioEmpleadoDTO InsertarItinerarioEmpleado(ItinerarioEmpleadoDTO ItiDto){
        try{
            ItinerarioEmpleadoEntities nuevoItiEmp = new ItinerarioEmpleadoEntities();

            //Asignacion de datos simples
            nuevoItiEmp.setIdItinerario(ItiDto.getIdItinerario());
            nuevoItiEmp.setDuiEmpleado(ItiDto.getDuiEmpleado());
            nuevoItiEmp.setIdRuta(ItiDto.getIdRuta());
            nuevoItiEmp.setIdVehiculo(ItiDto.getIdVehiculo());
            nuevoItiEmp.setFecha(ItiDto.getFecha());
            nuevoItiEmp.setHoraInicio(ItiDto.getHoraInicio());
            nuevoItiEmp.setHoraFin(ItiDto.getHoraFin());
            nuevoItiEmp.setObservaciones(ItiDto.getObservaciones());

            rep.save(nuevoItiEmp);
            return ItiDto;
        }
        catch (Exception e){
            return null;
        }
   }
   public ItinerarioEmpleadoDTO ActualizaItinerarioEmpleado(Long idItinerarioEmpleado, ItinerarioEmpleadoDTO ItiEmpDto){
        //Verificar existencai
       ItinerarioEmpleadoEntities ItiEmpExiste = rep.findById(idItinerarioEmpleado).orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Itinerario no encontrado"));

       //Actualizar Campos
       ItiEmpExiste.setIdItinerario(ItiEmpDto.getIdItinerario());
       ItiEmpExiste.setDuiEmpleado(ItiEmpDto.getDuiEmpleado());
       ItiEmpExiste.setIdRuta(ItiEmpDto.getIdRuta());
       ItiEmpExiste.setIdVehiculo(ItiEmpDto.getIdVehiculo());
       ItiEmpExiste.setFecha(ItiEmpDto.getFecha());
       ItiEmpExiste.setHoraInicio(ItiEmpDto.getHoraInicio());
       ItiEmpExiste.setHoraFin(ItiEmpDto.getHoraFin());
       ItiEmpExiste.setObservaciones(ItiEmpDto.getObservaciones());
      //Guardar Cambios
      ItinerarioEmpleadoEntities ItiEmpActualizar = rep.save(ItiEmpExiste);
      //Conversion a DTO
       return convertirAItinerarioEmpleadoDTO(ItiEmpActualizar);
   }

   public boolean EliminarItinerarioEmpleado(Long idItinerario){
        try{
            //Verificar existencia
            ItinerarioEmpleadoEntities objItiEmp = rep.findById(Long.valueOf(idItinerario)).orElse(null);
            //Si existe pprecede a eliminarse
            if (objItiEmp !=null){
                rep.deleteById(Long.valueOf(idItinerario));
                return true;
            }else{
                System.out.println("Itinerario de Empleado no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el itinerario de empleado con ID:" + idItinerario + "para eliminar",1);
        }
   }

    private ItinerarioEmpleadoDTO convertirAItinerarioEmpleadoDTO(ItinerarioEmpleadoEntities itiemp){
        ItinerarioEmpleadoDTO dto = new ItinerarioEmpleadoDTO();
        dto.setIdItinerario(itiemp.getIdItinerario());
        dto.setDuiEmpleado(itiemp.getDuiEmpleado());
        dto.setIdRuta(itiemp.getIdRuta());
        dto.setIdVehiculo(itiemp.getIdVehiculo());
        dto.setFecha(itiemp.getFecha());
        dto.setHoraInicio(itiemp.getHoraInicio());
        dto.setHoraFin(itiemp.getHoraFin());
        dto.setObservaciones(itiemp.getObservaciones());
        return dto;
    }

    private ItinerarioEmpleadoEntities convertirAItinerarioEmpleadoEntity(ItinerarioEmpleadoDTO dto){
        ItinerarioEmpleadoEntities ent = new ItinerarioEmpleadoEntities();

        ent.setIdItinerario(dto.getIdItinerario());
        ent.setDuiEmpleado(dto.getDuiEmpleado());
        ent.setIdRuta(dto.getIdRuta());
        ent.setIdVehiculo(dto.getIdVehiculo());
        ent.setFecha(dto.getFecha());
        ent.setHoraInicio(dto.getHoraInicio());
        ent.setHoraFin(dto.getHoraFin());
        ent.setObservaciones(dto.getObservaciones());

        return ent;
    }

}
