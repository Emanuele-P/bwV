package ep2024.bwV.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends Utente {
    @Id
    @GeneratedValue
    private UUID id;
    public Admin(String password, String email, String name, String username, String surname) {
        super(password, email, name, username, surname, "ADMIN");
    }
}
