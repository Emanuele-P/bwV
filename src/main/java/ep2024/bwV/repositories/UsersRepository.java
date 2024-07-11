package ep2024.bwV.repositories;

import ep2024.bwV.entities.Userrrrr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Userrrrr, UUID> {

    Optional<Userrrrr> findByEmail(String email);

    Optional<Userrrrr> findByNameAndSurname(String name, String surname);

    Optional<Userrrrr> findById(UUID id);
}
