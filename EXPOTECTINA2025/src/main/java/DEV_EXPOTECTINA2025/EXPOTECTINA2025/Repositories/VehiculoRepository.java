package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.LugarTuristicoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.VehiculosEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<VehiculosEntities, Long> {
}
