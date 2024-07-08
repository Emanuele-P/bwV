package ep2024.bwV.entities;


import ep2024.bwV.enums.TipoUtente;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @OneToOne(mappedBy = "utente")
    private String email;

    private String password;

    private String name;

    private String surname;

    private String username;

    private String avatar;

    private TipoUtente tipoUtente;

    public Utente(String password, String email, String name, String username, String surname, TipoUtente tipoUtente) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.username = username;
        this.surname = surname;
        this.tipoUtente = tipoUtente;
    }
}
