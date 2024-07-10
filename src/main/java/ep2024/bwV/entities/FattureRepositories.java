package ep2024.bwV.entities;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;


public interface FattureRepositories extends JpaRepository<Fatture, UUID> {
//    Optional<Fatture> findByStato(boolean stato);
    Optional<Fatture> findByNumero(long numero);
    Optional<Fatture> findById(UUID id);
}
