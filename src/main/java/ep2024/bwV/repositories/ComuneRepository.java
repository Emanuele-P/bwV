package ep2024.bwV.repositories;

import ep2024.bwV.entities.Comune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComuneRepository extends JpaRepository<Comune, UUID> {
    List<Comune> findByNome(String nome);
    void deleteByNome(String nome);
}
