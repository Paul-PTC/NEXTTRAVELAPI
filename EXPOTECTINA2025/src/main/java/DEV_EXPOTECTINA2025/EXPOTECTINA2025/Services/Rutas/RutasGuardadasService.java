package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Services.Rutas;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.RutasGuardadasEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Models.DTO.RutasGuardadasDTO;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutaRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.RutasGuardadasRepository;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RutasGuardadasService {

    @Autowired
    private RutasGuardadasRepository rutasRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private RutaRepository rutaRepository;


}

