package ep2024.bwV.repositories;

import ep2024.bwV.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProvinciaRepository extends JpaRepository<Provincia, UUID> {
    Optional<Provincia> findBySigla(String sigla);
    Optional<Provincia> findByName(String nome);
}
