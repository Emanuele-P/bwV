package ep2024.bwV.repositories;

import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.Fattura;
import ep2024.bwV.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface FattureRepositories extends JpaRepository<Fattura, UUID> {
//    Optional<Fattura> findByStato(boolean stato);
    Optional<Fattura> findByNumero(long numero);
    Optional<Fattura> findById(UUID id);
    List<Fattura> findAll();
    List<Fattura> findByCliente(Cliente cliente);
    @Query("SELECT f.stato FROM Fattura f WHERE f.id = :fatturaId")
    StatoFattura findStatoByFatturaId(@Param("fatturaId") UUID fatturaId);
}
