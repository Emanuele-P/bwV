package ep2024.bwV.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class User extends Utente {
    @Id
    @GeneratedValue
    private UUID id;

    private String avatar;


    public User(String password, String email, String name, String username, String surname, String ruolo, String avatar) {
        super(password, email, name, username, surname, ruolo);
        this.avatar = avatar;

    }


}
