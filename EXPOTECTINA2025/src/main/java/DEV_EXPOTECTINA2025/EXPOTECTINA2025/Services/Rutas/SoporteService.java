package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.SoporteEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Exceptions.ExceptionsSoporteNoEncontrado;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.SoporteDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.SoporteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SoporteService {
    @Autowired
    private SoporteRepository soporteRepository;

    public List<SoporteDTO> obtenerTodos() {
        List<SoporteEntity> soportes = soporteRepository.findAll();
        return soportes.stream()
                .map(this::convertirASoporteDTO)
                .collect(Collectors.toList());
    }

    private SoporteDTO convertirASoporteDTO(SoporteEntity entity) {
        SoporteDTO dto = new SoporteDTO();
        dto.setIdSoporte(entity.getIdSoporte());
        dto.setDuiCliente(entity.getDuiCliente());
        dto.setDuiEmpleado(entity.getDuiEmpleado());
        dto.setAsunto(entity.getAsunto());
        dto.setMensaje(entity.getMensaje());
        dto.setTipo(entity.getTipo());
        dto.setEstado(entity.getEstado());
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        return dto;
    }
    public SoporteDTO insertSoporte(SoporteDTO soporteDto) {
        if (soporteDto == null || soporteDto.getMensaje() == null || soporteDto.getMensaje().isEmpty()) {
            throw new IllegalArgumentException("Soporte o mensaje no pueden ser nulos o vacíos");
        }

        try {
            // Convertimos el DTO a entidad
            SoporteEntity soporteEntity = convertirASoporteEntity(soporteDto);

            // Guardamos el soporte
            SoporteEntity soporteGuardado = soporteRepository.save(soporteEntity);

            // Convertimos de nuevo a DTO para retornar
            return convertirASoporteDTO(soporteGuardado);
        } catch (Exception e) {
            log.error("Error al registrar soporte: " + e.getMessage());
            throw new RuntimeException("Error al registrar el soporte: " + e.getMessage());
        }
    }
    private SoporteEntity convertirASoporteEntity(SoporteDTO dto) {
        SoporteEntity entity = new SoporteEntity();

        // Mapeo directo
        entity.setIdSoporte(dto.getIdSoporte());
        entity.setDuiCliente(dto.getDuiCliente());
        entity.setDuiEmpleado(dto.getDuiEmpleado());
        entity.setAsunto(dto.getAsunto());
        entity.setMensaje(dto.getMensaje());
        entity.setTipo(dto.getTipo());
        entity.setEstado(dto.getEstado());
        entity.setFechaSolicitud(dto.getFechaSolicitud());

        return entity;
    }

    public SoporteDTO actualizarSoporte(Long id, SoporteDTO soporteDto) {
        // 1. Verificar existencia del soporte
        SoporteEntity soporteExistente = soporteRepository.findById(id)
                .orElseThrow(() -> new ExceptionsSoporteNoEncontrado("Soporte no encontrado"));

        // 2. Actualizar campos
        soporteExistente.setDuiCliente(soporteDto.getDuiCliente());
        soporteExistente.setDuiEmpleado(soporteDto.getDuiEmpleado());
        soporteExistente.setAsunto(soporteDto.getAsunto());
        soporteExistente.setMensaje(soporteDto.getMensaje());
        soporteExistente.setTipo(soporteDto.getTipo());
        soporteExistente.setEstado(soporteDto.getEstado());
        soporteExistente.setFechaSolicitud(soporteDto.getFechaSolicitud());

        // 3. Guardar los cambios
        SoporteEntity soporteActualizado = soporteRepository.save(soporteExistente);

        // 4. Convertir a DTO
        return convertirASoporteDTO(soporteActualizado);
    }
    public boolean removerSoporte(Long id) {
        try {
            // Se valida la existencia del soporte previamente a la eliminación
            SoporteEntity objSoporte = soporteRepository.findById(id).orElse(null);

            // Si objSoporte existe, se procede a eliminar
            if (objSoporte != null) {
                soporteRepository.deleteById(id);
                return true;
            } else {
                System.out.println("Soporte no encontrado.");
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("No se encontró soporte con ID: " + id + " para eliminar.", 1);
        }
    }

}
