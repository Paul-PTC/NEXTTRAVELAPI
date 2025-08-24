package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.TiempoEstimadoLlegadaEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiempoEstimadoLlegadaRepository extends JpaRepository<TiempoEstimadoLlegadaEntities, Long> {
}
