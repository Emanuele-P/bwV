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
public class StatoFatture {
@Id
@Setter(AccessLevel.NONE)
@GeneratedValue
private UUID id;
private boolean caricato;
private boolean in_gestione;
private boolean consegnato;
private boolean controllato;

    @OneToMany(mappedBy = "stato")
    @JsonIgnore
    private List<Fatture> fatture;

    public StatoFatture(boolean controllato, boolean caricato, boolean in_gestione, boolean consegnato) {
        this.controllato = controllato;
        this.caricato = caricato;
        this.in_gestione = in_gestione;
        this.consegnato = consegnato;
    }
}
