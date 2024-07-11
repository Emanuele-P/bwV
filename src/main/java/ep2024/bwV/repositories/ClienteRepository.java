package ep2024.bwV.repositories;

import ep2024.bwV.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByPec(String pec);

    Optional<Cliente> findByPartitaIva(int partitaIva);

    Optional<Cliente> findByFatturatoAnnuale(Long fatturatoAnnuale);

    Optional<Cliente> findByDataInserimento(LocalDate dataInserimento);

    Optional<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto);

    Optional<Cliente> findByNomeContattoStartingWithIgnoreCase(String partialNomeContatto);


}
