package ep2024.bwV.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fatture")
public class Fatture {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private UUID id;
    private LocalDateTime data;
    private double importo;
    private long numero;
    @ManyToOne
    @JoinColumn(name = "stato_id")
    private StatoFatture stato;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente ;


    public Fatture(double importo, LocalDateTime data,long numero) {
        this.importo = importo;
        this.data = data;
        this.numero=numero;

    }

}
