package ep2024.bwV.config;

import ep2024.bwV.entities.Role;
import ep2024.bwV.entities.User;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.repositories.ProvinciaRepository;
import ep2024.bwV.repositories.UsersRepository;
import ep2024.bwV.services.CsvService;
import ep2024.bwV.services.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Configuration
public class CsvDataInitializer {

    @Autowired
    private CsvService csvService;
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private UserRolesService rolesService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @PostConstruct
    public void init() {
        try {
            csvService.readProvince("data/province-italiane.csv");
            csvService.readComuni("data/comuni-italiani.csv");
            System.out.println("CSV files loaded successfully");

            if (!rolesService.findById("ADMIN").isPresent()) {
                rolesService.save(new Role("ADMIN"));
            }
            if (!rolesService.findById("USER").isPresent()) {
                rolesService.save(new Role("USER"));
            }

            if (!usersRepository.findByEmail("testadmin@email.com").isPresent()) {
                Role adminRole = rolesService.findByName("ADMIN");
                List<Role> roles = Collections.singletonList(adminRole);
                User adminUser = new User(
                        "testadmin@email.com",
                        bcrypt.encode("12345"),
                        "Admin",
                        "Hammer",
                        "banHammer",
                        "https://ui-avatars.com/api/?name=Admin+User",
                        roles
                );
                usersRepository.save(adminUser);
                System.out.println("Admin user initialized successfully");
            } else {
                System.out.println("Admin user already exists.");
            }
        } catch (BadRequestException e) {
            System.out.println("Initialization failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred during initialization: " + e.getMessage());
        }
    }
}
