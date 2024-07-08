package ep2024.bwV.repositories;

import ep2024.bwV.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Utente, UUID> {

    Optional<Utente> findByEmail(String email);
}
