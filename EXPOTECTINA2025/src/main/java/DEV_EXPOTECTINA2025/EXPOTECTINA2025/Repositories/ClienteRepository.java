package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.ClienteEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntities, Long> {
}
