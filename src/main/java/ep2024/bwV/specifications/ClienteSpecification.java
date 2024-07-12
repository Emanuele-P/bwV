package ep2024.bwV.specifications;

import ep2024.bwV.entities.Cliente;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ClienteSpecification {
    public static Specification<Cliente> getClientesByFilters(
            String nome,
            Double fatturatoAnnuale,
            LocalDate dataInserimento,
            LocalDate dataUltimoContatto,
            String provincia,
            Double minFatturatoAnnuale,
            LocalDate specificDataInserimento,
            LocalDate specificDataUltimoContatto
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nome != null) {
                predicates.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
            }
            if (fatturatoAnnuale != null) {
                predicates.add(criteriaBuilder.equal(root.get("fatturatoAnnuale"), fatturatoAnnuale));
            }
            if (minFatturatoAnnuale != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fatturatoAnnuale"), minFatturatoAnnuale));
            }
            if (dataInserimento != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataInserimento"), dataInserimento));
            }
            if (specificDataInserimento != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataInserimento"), specificDataInserimento));
            }
            if (dataUltimoContatto != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataUltimoContatto"), dataUltimoContatto));
            }
            if (specificDataUltimoContatto != null) {
                predicates.add(criteriaBuilder.equal(root.get("dataUltimoContatto"), specificDataUltimoContatto));
            }
            if (provincia != null) {
                predicates.add(criteriaBuilder.equal(root.get("indirizzoSedeLegale").get("localita"), provincia));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
