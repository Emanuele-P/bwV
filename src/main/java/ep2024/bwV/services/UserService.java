package ep2024.bwV.services;

import ep2024.bwV.entities.Utente;
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
public class UserService {

    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private PasswordEncoder bcrypt;

    public Page<Utente> getUsers(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return usersRepository.findAll(pageable);
    }

    public Utente save(NewUserDTO body) {
        // 1. Verifico se l'email è già in uso
        this.usersRepository.findByEmail(body.email()).ifPresent(
                // 1.1 Se lo è triggero un errore
                user -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );


        Utente newUser = new Utente(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()), body.username());

        newUser.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());


        return usersRepository.save(newUser);
    }

    public Utente findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public Utente findByIdAndUpdate(UUID userId, Utente modifiedUser) {
        Utente found = this.findById(userId);
        found.setName(modifiedUser.getName());
        found.setSurname(modifiedUser.getSurname());
        found.setEmail(modifiedUser.getEmail());
        found.setPassword(modifiedUser.getPassword());
        found.setAvatar("https://ui-avatars.com/api/?name=" + modifiedUser.getName() + "+" + modifiedUser.getSurname());
        return this.usersRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        Utente found = this.findById(userId);
        this.usersRepository.delete(found);
    }

    public Utente findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

}
