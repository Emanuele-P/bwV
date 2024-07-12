package ep2024.bwV.specifications;

import ep2024.bwV.entities.Cliente;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ClienteSpecification {
    public static Specification<Cliente> getClientesByFilters(String nome, Long fatturatoAnnuale, LocalDate dataInserimento, LocalDate dataUltimoContatto, String provincia) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nome != null) {
                predicates.add(criteriaBuilder.equal(root.get("ragioneSociale"), nome));
            }
            if (fatturatoAnnuale != null) {
                predicates.add(criteriaBuilder.equal(root.get("fatturatoAnnuale"), fatturatoAnnuale));
            }
            if (dataInserimento != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataInserimento"), dataInserimento));
            }
            if (dataUltimoContatto != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataUltimoContatto"), dataUltimoContatto));
            }
            if (provincia != null) {
                predicates.add(criteriaBuilder.equal(root.get("indirizzoSedeLegale").get("provincia"), provincia));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
