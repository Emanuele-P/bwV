package ep2024.bwV.services;

import ep2024.bwV.entities.Utente;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.repositories.AdminRepository;
import ep2024.bwV.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Utente findById(UUID id) {
        return this.usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
