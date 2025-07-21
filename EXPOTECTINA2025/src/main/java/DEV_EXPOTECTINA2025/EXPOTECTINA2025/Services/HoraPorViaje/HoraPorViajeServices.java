package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.HoraPorViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.HorasPorViajeEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ItinerarioEmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutaEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsHorasPorViajeNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.HorasPorViajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.HorasPorViajeRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HoraPorViajeServices {
    @Autowired
    private HorasPorViajeRepository repo;

    @Autowired
    private EmpleadoRepository EmpRep;

    @Autowired
    private RutaRepository RutRepo;

    public List<HorasPorViajeDTO> getAllHorasPorViaje(){
        List<HorasPorViajeEntities> datos = repo.findAll();
        return datos.stream()
                .map(this::convertirAHorasDTO)
                .collect(Collectors.toList());
    }
     public HorasPorViajeDTO InsertarHorasPorViaje(HorasPorViajeDTO Dto){
        try{
            HorasPorViajeEntities nuevasHorasPorViajes = new HorasPorViajeEntities();
             //aIGNAMOS LOS DATOS
            nuevasHorasPorViajes.setIdHorasViaje(Dto.getIdHorasViaje());
            nuevasHorasPorViajes.setHoraSalida(Dto.getHoraSalida());
            nuevasHorasPorViajes.setHoraLlegada(Dto.getHoraLlegada());
            nuevasHorasPorViajes.setEmpleado(Dto.getEmpleado());
            nuevasHorasPorViajes.setDuracion(Dto.getDuracion());
            nuevasHorasPorViajes.setFechaViaje((Date) Dto.getFechaViaje());

            //Relacion con EmpleadoEntities
            Optional<EmpleadoEntities> EmpleadoEncontrado = EmpRep.findById(String.valueOf(Dto.getEmpleado()));
            if (EmpRep != null) {
                nuevasHorasPorViajes.setEmpleado((EmpleadoEntities) EmpRep);
            }else {
                return null;//No encontrado
            }

            //Relacion RutaEntities
            Optional<RutaEntities> rutaEncontrada = RutRepo.findById(Dto.getIdRuta());
            if (rutaEncontrada.isPresent()){
                nuevasHorasPorViajes.setIdRuta(rutaEncontrada.get());
            }else {
                return null;
            }
            repo.save(nuevasHorasPorViajes);
            return Dto;
        }catch (Exception e){
            return null;
        }
     }

     public HorasPorViajeDTO ActualizarHorasPorViaje(Long idHorasViaje,HorasPorViajeDTO horasViajedto) {
         HorasPorViajeEntities HorasViajeExistentes = repo.findById(idHorasViaje).orElseThrow(() -> new ExceptionsHorasPorViajeNoEncontrado("Horas No encontradas"));

         //Actualizar campos
         HorasViajeExistentes.setIdHorasViaje(horasViajedto.getIdHorasViaje());
         HorasViajeExistentes.setHoraSalida(horasViajedto.getHoraSalida());
         HorasViajeExistentes.setHoraLlegada(horasViajedto.getHoraLlegada());
         HorasViajeExistentes.setEmpleado(horasViajedto.getEmpleado());
         HorasViajeExistentes.setDuracion(horasViajedto.getDuracion());
         HorasViajeExistentes.setFechaViaje((Date) horasViajedto.getFechaViaje());

         //Relacion con Empleado
         if (horasViajedto.getEmpleado() != null) {
             EmpleadoEntities Emp = EmpRep.findById(String.valueOf(horasViajedto.getEmpleado()))
                     .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con el ID proporcionado"));
             HorasViajeExistentes.setEmpleado(Emp);
         } else {
             HorasViajeExistentes.setEmpleado(null);
         }
         //Actualizar con rango
         RutaEntities ruta = null;
         if (horasViajedto.getIdRuta() != null) {
             ruta = RutRepo.findById(horasViajedto.getIdRuta())
                     .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrado con ID propocionado"));
             HorasViajeExistentes.setIdRuta(ruta);
         } else {
             HorasViajeExistentes.setIdRuta(null);
         }
         HorasPorViajeEntities actualizarHoras = repo.save(HorasViajeExistentes);
         return convertirAHorasDTO(actualizarHoras);
     }

    public HorasPorViajeDTO ActualizarHorasPorViajes(Long idHorasViaje, HorasPorViajeDTO horasViajdto) {
        // Buscar entidad existente
        HorasPorViajeEntities horasViajeExistentes = repo.findById(idHorasViaje)
                .orElseThrow(() -> new ExceptionsHorasPorViajeNoEncontrado("Horas no encontradas con ID: " + idHorasViaje));

        // Actualizar campos básicos
        horasViajeExistentes.setIdHorasViaje(horasViajdto.getIdHorasViaje());
        horasViajeExistentes.setHoraSalida(horasViajdto.getHoraSalida());
        horasViajeExistentes.setHoraLlegada(horasViajdto.getHoraLlegada());
        horasViajeExistentes.setEmpleado(horasViajdto.getEmpleado());
        horasViajeExistentes.setDuracion(horasViajdto.getDuracion());
        horasViajeExistentes.setFechaViaje((Date) horasViajdto.getFechaViaje());

        // Relación con Empleado
        if (horasViajdto.getEmpleado() != null) {
            EmpleadoEntities emp = EmpRep.findById(String.valueOf(horasViajdto.getEmpleado()))
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con el ID proporcionado"));
            horasViajeExistentes.setEmpleado(emp);
        } else {
            horasViajeExistentes.setEmpleado(null);
        }

        // Relación con Ruta
        if (horasViajdto.getIdRuta() != null) {
            RutaEntities ruta = RutRepo.findById(horasViajdto.getIdRuta())
                    .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con ID proporcionado"));
            horasViajeExistentes.setIdRuta(ruta);
        } else {
            horasViajeExistentes.setIdRuta(null);
        }

        // Guardar cambios
        HorasPorViajeEntities actualizarHoras = repo.save(horasViajeExistentes);

        // Retornar DTO actualizado
        return convertirAHorasDTO(actualizarHoras);
    }


     public boolean EliminarHorasPorViaje(Long idHoraViaje){
        try{
            //Validamos existencia
            RutaEntities objRutas = Objects.requireNonNull(repo.findById(idHoraViaje).orElse(null)).getIdRuta();
            //Si existe se elimina
            if(objRutas != null){
                repo.deleteById(idHoraViaje);
                return true;
            }else {
                System.out.println("Horas de viaje no encontradas");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
         throw  new EmptyResultDataAccessException("No se Encontraron Horas de viaje con ID" + idHoraViaje + "para eliminar", 1);
     }
    }

    //Convertir Entity a DTO
    private HorasPorViajeDTO convertirAHorasDTO(HorasPorViajeEntities horas){
        HorasPorViajeDTO dto = new HorasPorViajeDTO();

        dto.setIdHorasViaje(horas.getIdHorasViaje());
        dto.setHoraSalida(horas.getHoraSalida());
        dto.setHoraLlegada(horas.getHoraLlegada());
        dto.setEmpleado(horas.getEmpleado());
        dto.setDuracion(horas.getDuracion());
        dto.setFechaViaje((Date) horas.getFechaViaje());

        return dto;
    }

    //Converitr DTO A ENTITYYYY

    private HorasPorViajeEntities convertirAHorasEntity (HorasPorViajeDTO dto){
        HorasPorViajeEntities ent = new HorasPorViajeEntities();

        dto.setIdHorasViaje(ent.getIdHorasViaje());
        dto.setHoraSalida(ent.getHoraSalida());
        dto.setHoraLlegada(ent.getHoraLlegada());
        dto.setEmpleado(ent.getEmpleado());
        dto.setDuracion(ent.getDuracion());
        dto.setFechaViaje((Date) ent.getFechaViaje());
        if (dto.getEmpleado() != null){
            EmpleadoEntities emp = EmpRep.findById(String.valueOf(dto.getEmpleado()))
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + dto.getEmpleado()));
            ent.setEmpleado(emp);
        }
        if (dto.getIdRuta() != null){
            RutaEntities ruta = RutRepo.findById(dto.getIdRuta())
                    .orElseThrow(() -> new IllegalArgumentException("RUTA no encontrado con ID: " + dto.getIdRuta()));
            ent.setIdRuta(ruta);
        }
        return  ent;
    }

}



