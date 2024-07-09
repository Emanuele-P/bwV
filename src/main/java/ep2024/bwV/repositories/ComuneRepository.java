package ep2024.bwV.repositories;

import ep2024.bwV.entities.Comune;
import ep2024.bwV.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComuneRepository extends JpaRepository<Comune, UUID> {
    List<Comune> findByNome(String nome);
    Optional<Comune> findByNomeAndProvincia(String nome, Provincia provincia);
    void deleteByNome(String nome);
}
