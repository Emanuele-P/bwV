package ep2024.bwV.services;

import ep2024.bwV.entities.Admin;
import ep2024.bwV.entities.User;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewUtenteDTO;
import ep2024.bwV.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Admin save(NewUtenteDTO body) {

        adminRepository.findByEmail(body.email()).ifPresent(
                admin -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );
        Admin newAdmin = new Admin(body.email(), bcrypt.encode(body.password()), body.name(), body.surname(), body.username());
        return adminRepository.save(newAdmin);
    }

    public Admin findById(UUID adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new NotFoundException(adminId));
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Admin con email " + email + " non trovato!"));
    }
}
