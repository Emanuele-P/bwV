package ep2024.bwV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Indirizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String via;
    private String civico;
    private String localita;
    private String cap;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "comune_id")
    private Comune comune;

    @Column(name = "nome_comune")
    private String nomeComune;


    public Indirizzo(String via, String civico, String nomeProvincia, String cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = nomeProvincia;
        this.cap = cap;
        this.comune = comune;
        this.nomeComune = comune.getNome();
    }
}
