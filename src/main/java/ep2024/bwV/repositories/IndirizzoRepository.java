package ep2024.bwV.repositories;

import ep2024.bwV.entities.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, UUID> {
}
