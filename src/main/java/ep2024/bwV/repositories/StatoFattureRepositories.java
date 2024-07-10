package ep2024.bwV.repositories;

import ep2024.bwV.entities.StatoFatture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StatoFattureRepositories extends JpaRepository<StatoFatture, UUID> {
    Optional<StatoFatture> findById(UUID id);
}
