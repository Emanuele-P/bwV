package ep2024.bwV.repositories;

import ep2024.bwV.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProvinciaRepository extends JpaRepository<Provincia, UUID> {
    Provincia findBySigla(String sigla);
}
