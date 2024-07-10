package ep2024.bwV.services;

import ep2024.bwV.entities.User;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewUserDTO;
import ep2024.bwV.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> getUsers(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return usersRepository.findAll(pageable);
    }

    public User save(NewUserDTO body) {
        usersRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );
        usersRepository.findByNameAndSurname(body.name(), body.surname()).ifPresent(
                user -> {
                    throw new BadRequestException("L'utente " + body.name() + body.surname() + " è già registrato");
                }
        );

        User newUser = new User(bcrypt.encode(body.password()), body.email(), body.name(), body.surname(), body.username(), "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        return usersRepository.save(newUser);
    }

    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(UUID userId, NewUserDTO modifiedUser) {
        User found = this.findById(userId);
        found.setName(modifiedUser.name());
        found.setSurname(modifiedUser.surname());
        found.setEmail(modifiedUser.email());
        found.setPassword(bcrypt.encode(modifiedUser.password()));
        found.setAvatar("https://ui-avatars.com/api/?name=" + modifiedUser.name() + "+" + modifiedUser.surname());
        return this.usersRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        this.usersRepository.delete(found);
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
}
