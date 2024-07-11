package ep2024.bwV.repositories;

import ep2024.bwV.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatoFattureRepositories extends JpaRepository<StatoFattura, UUID> {
    Optional<StatoFattura> findById(UUID id);
}
