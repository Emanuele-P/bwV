package ep2024.bwV.repositories;

import ep2024.bwV.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminsRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findById(UUID id);
}
