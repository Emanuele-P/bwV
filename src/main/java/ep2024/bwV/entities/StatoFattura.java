package ep2024.bwV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stato_fatture")
@Getter
@Setter
@NoArgsConstructor
public class StatoFattura {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private UUID id;

    private String stato;

    public StatoFattura(String stato) {
        this.stato = stato;
    }
}
