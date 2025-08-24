package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Cliente;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UserEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.ClienteDTO;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.ClienteRepository;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteServices {
    @Autowired
    private ClienteRepository repo;

    @Autowired
    private UserRepository userRepository;

    public List<ClienteDTO> getAllEmpleados() {
        List<ClienteEntities> cliente = repo.findAll();
        return cliente.stream()
                .map(this::convertirAClienteDTO)
                .collect(Collectors.toList());

    }

    public ClienteDTO convertirAClienteDTO(ClienteEntities cliente) {
        ClienteDTO dto = new ClienteDTO();

        dto.setDuiCliente(cliente.getDuiCliente());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setCorreo(cliente.getCorreo());
        dto.setGenero(cliente.getGenero());
        dto.setEdad(cliente.getEdad());
        dto.setEstado(cliente.getEstado());
        dto.setDireccion(cliente.getDireccion());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        dto.setFotoPerfil(cliente.getFotoPerfil());

        // Manejo seguro de usuario
        if (cliente.getUsuario() != null) {
            dto.setIdUsuario(cliente.getUsuario().getId());
        } else {
            dto.setIdUsuario(null); // o lanza una excepción si es obligatorio
        }

        return dto;
    }

    public ClienteEntities insertarCliente(ClienteDTO dto) {
        ClienteEntities entity = new ClienteEntities();

        entity.setDuiCliente(dto.getDuiCliente());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setCorreo(dto.getCorreo());
        entity.setGenero(dto.getGenero());
        entity.setEdad(dto.getEdad());
        entity.setEstado(dto.getEstado());
        entity.setDireccion(dto.getDireccion());
        entity.setFechaRegistro(dto.getFechaRegistro());
        entity.setFotoPerfil(dto.getFotoPerfil());


        UserEntity usuario = userRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getIdUsuario()));
        entity.setUsuario(usuario);

        return repo.save(entity);
    }

    public ClienteEntities convertirAClienteEntity(ClienteDTO dto) {
        ClienteEntities entity = new ClienteEntities();

        entity.setDuiCliente(dto.getDuiCliente());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setCorreo(dto.getCorreo());
        entity.setGenero(dto.getGenero());
        entity.setEdad(dto.getEdad());
        entity.setEstado(dto.getEstado());
        entity.setDireccion(dto.getDireccion());
        entity.setFechaRegistro(dto.getFechaRegistro());
        entity.setFotoPerfil(dto.getFotoPerfil());

        // ✅ Aquí se busca el usuario y se asigna
        UserEntity usuario = repo.findById(String.valueOf(dto.getIdUsuario()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getIdUsuario())).getUsuario();
        entity.setUsuario(usuario);

        return entity;
    }


public ClienteDTO actualizarCliente(String dui, ClienteDTO dto) {
    ClienteEntities cliente = repo.findById(dui)
            .orElseThrow(() -> new NoSuchElementException("No se encontró cliente con DUI: " + dui));

    cliente.setNombre(dto.getNombre());
    cliente.setApellido(dto.getApellido());
    cliente.setTelefono(dto.getTelefono());
    cliente.setCorreo(dto.getCorreo());
    cliente.setGenero(dto.getGenero());
    cliente.setEdad(dto.getEdad());
    cliente.setEstado(dto.getEstado());
    cliente.setDireccion(dto.getDireccion());
    cliente.setFechaRegistro(dto.getFechaRegistro());
    cliente.setFotoPerfil(dto.getFotoPerfil());

    UserEntity usuario = userRepository.findById(Long.valueOf(String.valueOf(dto.getIdUsuario())))
            .orElseThrow(() -> new NoSuchElementException("No se encontró usuario con ID: " + dto.getIdUsuario()));
    cliente.setUsuario(usuario);

    ClienteEntities actualizado = repo.save(cliente);
    return convertirAClienteDTO(actualizado);
}

public boolean eliminarCliente(String dui) {
    Optional<ClienteEntities> cliente = repo.findById((dui));
    if (cliente.isPresent()) {
        repo.deleteById((dui));
        return true;
    }
    return false;
}


}



