package ep2024.bwV.repositories;

import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.Fatture;
import ep2024.bwV.entities.StatoFatture;
import ep2024.bwV.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface FattureRepositories extends JpaRepository<Fatture, UUID> {
    List<Fatture> findByStato(StatoFatture statoFatture);
    Optional<Fatture> findByNumero(long numero);
    Optional<Fatture> findById(UUID id);
    List<Fatture> findAll();
    List<Fatture> findByCliente(Cliente cliente);
    @Query("SELECT f.stato FROM Fatture f WHERE f.id = :fatturaId")
    StatoFatture findStatoByFatturaId(@Param("fatturaId") UUID fatturaId);
}
