package ep2024.bwV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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

    public Fatture(double importo, LocalDateTime data,long numero) {
        this.importo = importo;
        this.data = data;
        this.numero=numero;

    }

}
