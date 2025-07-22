package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories.UbicacionEmpleado;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.UbicacionEmpleado.EntityUbicacionEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUbicacionEmpleado extends JpaRepository<EntityUbicacionEmpleado, Long> { }
