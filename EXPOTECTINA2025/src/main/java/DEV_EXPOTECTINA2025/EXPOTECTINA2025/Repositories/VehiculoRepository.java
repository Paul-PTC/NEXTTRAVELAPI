package DEV_EXPOTECTINA2025.EXPOTECTINA2025.Repositories;

import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.LugarTuristicoEntity;
import DEV_EXPOTECTINA2025.EXPOTECTINA2025.Entities.VehiculosEntities;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<VehiculosEntities, Long> {
    boolean existsByPlaca(@NotBlank(message = "La placa es obligatoria") @Size(max = 10, message = "La placa no debe exceder los 10 caracteres") String placa);
}
