package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Devuelve true si existe un registro con ese usuario
    boolean existsByUsuario(String usuario);

    // Devuelve true si existe un registro con ese correo
    boolean existsByCorreo(String correo);

    // (Opcionales, por si los necesitás)
    Optional<UserEntity> findByUsuario(String usuario);
    Optional<UserEntity> findByCorreo(String correo);
}
