package ep2024.bwV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User extends Utente {
    @Id
    @GeneratedValue
    private UUID id;
    private String avatar;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Cliente cliente;

    public User(String email, String password, String name, String surname, String username, String avatar) {
        super(email, password, name, surname, username, "USER");
        this.avatar = avatar;
    }
}
