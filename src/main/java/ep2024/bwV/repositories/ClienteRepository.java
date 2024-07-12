package ep2024.bwV.repositories;

import ep2024.bwV.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID>, JpaSpecificationExecutor<Cliente> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByPec(String pec);
}
