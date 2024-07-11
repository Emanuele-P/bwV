package ep2024.bwV.repositories;

import ep2024.bwV.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRolesRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
