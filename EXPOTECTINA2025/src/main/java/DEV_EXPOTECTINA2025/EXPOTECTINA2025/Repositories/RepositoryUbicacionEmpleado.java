package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EntitesUbicacionEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryUbicacionEmpleado extends JpaRepository<EntitesUbicacionEmpleado, Long> {
    Optional<EntitesUbicacionEmpleado> findByDui(String dui);
}
