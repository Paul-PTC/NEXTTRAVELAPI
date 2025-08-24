package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.HoraPorViaje;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.*;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsHorasPorViajeNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsItinerarioEmpleadoNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.HorasPorViajeDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ItinerarioEmpleadoDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.EmpleadoRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.HorasPorViajeRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;


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
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<HorasPorViajeDTO> getAllHorasPorViaje() {
        List<HorasPorViajeEntities> datos = repo.findAll();
        return datos.stream()
                .map(this::convertirAHorasDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/InsertarHorasViaje")
    public HorasPorViajeDTO InsertarHorasPorViaje(HorasPorViajeDTO dto) {
        try {
            HorasPorViajeEntities nuevasHorasPorViajes = new HorasPorViajeEntities();

            // Asignación de datos básicos
            nuevasHorasPorViajes.setIdHorasViaje(dto.getIdHorasViaje());
            nuevasHorasPorViajes.setHoraSalida(dto.getHoraSalida());
            nuevasHorasPorViajes.setHoraLlegada(dto.getHoraLlegada());
            nuevasHorasPorViajes.setDuracion(dto.getDuracion());

            // Convertir java.util.Date a java.sql.Date
            nuevasHorasPorViajes.setFechaViaje(new java.sql.Date(dto.getFechaViaje().getTime()));

            // Relación con Empleado - buscar empleado por DUI (String)
            Optional<EmpleadoEntities> empleadoEncontrado = EmpRep.findById(dto.getEmpleado());
            if (empleadoEncontrado.isEmpty()) {
                return null; // Empleado no encontrado
            }
            nuevasHorasPorViajes.setEmpleado(empleadoEncontrado.get());

            // Relación con Vehiculo - buscar Vehiculo por Id
            Optional<VehiculosEntities> vehiculoEncontrado = vehiculoRepository.findById(dto.getIdVehiculo());
            if (vehiculoEncontrado.isEmpty()) {
                return null; // Vehículo no encontrado
            }
            nuevasHorasPorViajes.setIdVehiculo(vehiculoEncontrado.get());

            // Relación con Ruta - buscar Ruta por Id
            Optional<RutaEntities> rutaEncontrada = RutRepo.findById(dto.getIdRuta());
            if (rutaEncontrada.isEmpty()) {
                return null; // Ruta no encontrada
            }
            nuevasHorasPorViajes.setIdRuta(rutaEncontrada.get());

            // Guardar entidad
            repo.save(nuevasHorasPorViajes);

            return dto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public HorasPorViajeDTO ActualizarHorasPorViaje(Long idHorasViaje, HorasPorViajeDTO horasViajedto) {
        // Buscar entidad existente o lanzar excepción si no existe
        HorasPorViajeEntities horasViajeExistentes = repo.findById(idHorasViaje)
                .orElseThrow(() -> new ExceptionsHorasPorViajeNoEncontrado("Horas No encontradas"));

        // Actualizar campos básicos
        horasViajeExistentes.setHoraSalida(horasViajedto.getHoraSalida());
        horasViajeExistentes.setHoraLlegada(horasViajedto.getHoraLlegada());
        horasViajeExistentes.setDuracion(horasViajedto.getDuracion());

        // Convertir java.util.Date a java.sql.Date para fechaViaje
        if (horasViajedto.getFechaViaje() != null) {
            horasViajeExistentes.setFechaViaje(new java.sql.Date(horasViajedto.getFechaViaje().getTime()));
        } else {
            horasViajeExistentes.setFechaViaje(null);
        }

        // Actualizar relación con Empleado (buscar por DUI, que es String en DTO)
        if (horasViajedto.getEmpleado() != null) {
            EmpleadoEntities empleado = EmpRep.findById(horasViajedto.getEmpleado())
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con el DUI proporcionado"));
            horasViajeExistentes.setEmpleado(empleado);
        } else {
            horasViajeExistentes.setEmpleado(null);
        }

        // Actualizar relación con Ruta
        if (horasViajedto.getIdRuta() != null) {
            RutaEntities ruta = RutRepo.findById(horasViajedto.getIdRuta())
                    .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con el ID proporcionado"));
            horasViajeExistentes.setIdRuta(ruta);
        } else {
            horasViajeExistentes.setIdRuta(null);
        }

        // Actualizar relación con Vehiculo (agregado, porque faltaba)
        if (horasViajedto.getIdVehiculo() != null) {
            VehiculosEntities vehiculo = vehiculoRepository.findById(horasViajedto.getIdVehiculo())
                    .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con el ID proporcionado"));
            horasViajeExistentes.setIdVehiculo(vehiculo);
        } else {
            horasViajeExistentes.setIdVehiculo(null);
        }

        // Guardar entidad actualizada
        HorasPorViajeEntities actualizarHoras = repo.save(horasViajeExistentes);

        // Convertir entidad a DTO y retornar
        return convertirAHorasDTO(actualizarHoras);
    }


    public boolean EliminarHorasPorViaje(Long idHoraViaje) {
        try {
            //Validamos existencia
            RutaEntities objRutas = Objects.requireNonNull(repo.findById(idHoraViaje).orElse(null)).getIdRuta();
            //Si existe se elimina
            if (objRutas != null) {
                repo.deleteById(idHoraViaje);
                return true;
            } else {
                System.out.println("Horas de viaje no encontradas");
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("No se Encontraron Horas de viaje con ID" + idHoraViaje + "para eliminar", 1);
        }
    }

    //Convertir Entity a DTO
    private HorasPorViajeDTO convertirAHorasDTO(HorasPorViajeEntities horas) {
        HorasPorViajeDTO dto = new HorasPorViajeDTO();

        dto.setIdHorasViaje(horas.getIdHorasViaje());
        dto.setHoraSalida(horas.getHoraSalida());
        dto.setHoraLlegada(horas.getHoraLlegada());
        dto.setEmpleado(String.valueOf(horas.getEmpleado()));
        dto.setDuracion(horas.getDuracion());
        dto.setFechaViaje((Date) horas.getFechaViaje());

        return dto;
    }

    //Converitr DTO A ENTITYYYY

    private HorasPorViajeEntities convertirAHorasEntity(HorasPorViajeDTO dto) {
        HorasPorViajeEntities ent = new HorasPorViajeEntities();

        dto.setIdHorasViaje(ent.getIdHorasViaje());
        dto.setHoraSalida(ent.getHoraSalida());
        dto.setHoraLlegada(ent.getHoraLlegada());
        dto.setEmpleado(String.valueOf(ent.getEmpleado()));
        dto.setDuracion(ent.getDuracion());
        dto.setFechaViaje((Date) ent.getFechaViaje());
        if (dto.getEmpleado() != null) {
            EmpleadoEntities emp = EmpRep.findById(String.valueOf(dto.getEmpleado()))
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + dto.getEmpleado()));
            ent.setEmpleado(emp);
        }
        if (dto.getIdRuta() != null) {
            RutaEntities ruta = RutRepo.findById(dto.getIdRuta())
                    .orElseThrow(() -> new IllegalArgumentException("RUTA no encontrado con ID: " + dto.getIdRuta()));
            ent.setIdRuta(ruta);
        }
        return ent;
    }

}



