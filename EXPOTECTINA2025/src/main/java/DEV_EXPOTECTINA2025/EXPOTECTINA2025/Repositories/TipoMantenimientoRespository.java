package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.EmpleadoEntities;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.TipoMantenimietoEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMantenimientoRespository extends JpaRepository<TipoMantenimietoEntities, Long> {
}
