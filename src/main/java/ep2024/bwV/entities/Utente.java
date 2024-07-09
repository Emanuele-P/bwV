package ep2024.bwV.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String username;

    private String ruolo;


    public Utente(String password, String email, String name, String username, String surname, String ruolo) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.username = username;
        this.surname = surname;
        this.ruolo = ruolo;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
