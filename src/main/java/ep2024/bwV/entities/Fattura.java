package ep2024.bwV.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fatture")
public class Fattura {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private UUID id;
    private LocalDate data;
    private double importo;
    private long numero;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stato_id")
    private StatoFattura stato;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente ;

    public Fattura(double importo, LocalDate data, long numero) {
        this.importo = importo;
        this.data = data;
        this.numero = numero;
    }
}
