package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Empleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ItinerarioEmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.VehiculosEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
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
    @Autowired
    private EmpleadoRepository EmpRepo;

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
            // El campo duiEmpleado es una relación @ManyToOne hacia EmpleadoEntities
            // No se puede asignar un String directamente, así que creamos un objeto EmpleadoEntities,
            // le seteamos el DUI que viene del DTO y lo asignamos
            EmpleadoEntities empleado = new EmpleadoEntities();
            empleado.setDuiEmpleado(ItiDto.getDuiEmpleado());
            nuevoItiEmp.setDuiEmpleado(empleado);
            // El campo idRuta también es una relación @ManyToOne hacia RutaEntities
            // Creamos un objeto RutaEntities y le seteamos el ID desde el DTO
            RutaEntities ruta = new RutaEntities();
            ruta.setIdRuta(ItiDto.getIdRuta());
            nuevoItiEmp.setIdRuta(ruta);
            // El campo vehiculo es una relación @ManyToOne hacia VehiculosEntities
            // Creamos un objeto VehiculosEntities y le seteamos el ID desde el DTO
            VehiculosEntities vehiculo = new VehiculosEntities();
            vehiculo.setIdVehiculo(Math.toIntExact(ItiDto.getIdVehiculo()));
            //
            nuevoItiEmp.setVehiculo(vehiculo);
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
    public ItinerarioEmpleadoDTO actualizarParcialItinerarioEmpleado(Long idItinerario, ItinerarioEmpleadoDTO dto)
            throws ExceptionsItinerarioEmpleadoNoEncontrado {

        ItinerarioEmpleadoEntities ent = rep.findById(idItinerario)
                .orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Itinerario no encontrado con ID: " + idItinerario));

        // Solo actualiza si los valores vienen con datos (no null)
        if (dto.getDuiEmpleado() != null) {
            EmpleadoEntities emp = new EmpleadoEntities();
            emp.setDuiEmpleado(dto.getDuiEmpleado()); // Seteamos el ID (DUI)
            ent.setDuiEmpleado(emp); // Asignamos el objeto a la relación ManyToOne
        }

        if (dto.getIdVehiculo() != null) {
            VehiculosEntities veh = new VehiculosEntities();
            veh.setIdVehiculo(Math.toIntExact(dto.getIdVehiculo()));
            ent.setVehiculo(veh);
        }

        if (dto.getIdRuta() != null) {
            RutaEntities ruta = new RutaEntities();
            ruta.setIdRuta(dto.getIdRuta());
            ent.setIdRuta(ruta);
        }

        if (dto.getFecha() != null) {
            ent.setFecha(dto.getFecha());
        }

        if (dto.getHoraInicio() != null) {
            ent.setHoraInicio(dto.getHoraInicio());
        }

        if (dto.getHoraFin() != null) {
            ent.setHoraFin(dto.getHoraFin());
        }

        if (dto.getObservaciones() != null) {
            ent.setObservaciones(dto.getObservaciones());
        }

        // Guarda los cambios
        rep.save(ent);

        // Convierte a DTO si es necesario
        return convertirAItinerarioEmpleadoDTO(ent);
    }

    public ItinerarioEmpleadoDTO ActualizaItinerarioEmpleado(Long idItinerarioEmpleado, ItinerarioEmpleadoDTO ItiEmpDto){
        //Verificar existencai
       ItinerarioEmpleadoEntities ItiEmpExiste = rep.findById(idItinerarioEmpleado).orElseThrow(() -> new ExceptionsItinerarioEmpleadoNoEncontrado("Itinerario no encontrado"));

       //Actualizar Campos
       ItiEmpExiste.setIdItinerario(ItiEmpDto.getIdItinerario());
       /// ////////////////////////////////////////////////
        /*@ManyToOne, JPA necesita un objeto completo para mapear la relación, aunque solo se use su clave primaria*/
        EmpleadoEntities emp = new EmpleadoEntities();
        emp.setDuiEmpleado(ItiEmpDto.getDuiEmpleado());
        ItiEmpExiste.setDuiEmpleado(emp);
        /// /////////////////////////////////////////////
        VehiculosEntities vehiculo = new VehiculosEntities();
        vehiculo.setIdVehiculo(Math.toIntExact(ItiEmpDto.getIdVehiculo()));
        ItiEmpExiste.setVehiculo(vehiculo);
      /// ////////////////////////////////////////////////////////////
        RutaEntities ruta = new RutaEntities();
        ruta.setIdRuta(ItiEmpDto.getIdRuta());
        ItiEmpExiste.setIdRuta(ruta);
        /// ////////////////////////////////////////////////////////////
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
        dto.setDuiEmpleado(itiemp.getDuiEmpleado().getDuiEmpleado()); // String
        dto.setIdRuta(itiemp.getIdRuta().getIdRuta()); // Long
        dto.setIdVehiculo(Long.valueOf(itiemp.getVehiculo().getIdVehiculo()));
        dto.setFecha(itiemp.getFecha());
        dto.setHoraInicio(itiemp.getHoraInicio());
        dto.setHoraFin(itiemp.getHoraFin());
        dto.setObservaciones(itiemp.getObservaciones());
        return dto;
    }

    private ItinerarioEmpleadoEntities convertirAItinerarioEmpleadoEntity(ItinerarioEmpleadoDTO dto){
        ItinerarioEmpleadoEntities ent = new ItinerarioEmpleadoEntities();

        ent.setIdItinerario(dto.getIdItinerario());
        /*@ManyToOne, JPA espera un objeto completo, no solo un ID.
Si le das solo el String o Long, te lanza un error de tipo incompatible.*/
        EmpleadoEntities emp = new EmpleadoEntities();
        emp.setDuiEmpleado(dto.getDuiEmpleado());
        ent.setDuiEmpleado(emp);

        RutaEntities ruta = new RutaEntities();
        ruta.setIdRuta(dto.getIdRuta());
        ent.setIdRuta(ruta);

        VehiculosEntities veh = new VehiculosEntities();
        veh.setIdVehiculo(Math.toIntExact(dto.getIdVehiculo()));
        ent.setVehiculo(veh);
        ent.setFecha(dto.getFecha());
        ent.setHoraInicio(dto.getHoraInicio());
        ent.setHoraFin(dto.getHoraFin());
        ent.setObservaciones(dto.getObservaciones());

        return ent;
    }

}
