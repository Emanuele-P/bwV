package ep2024.bwV.config;

import ep2024.bwV.entities.Role;
import ep2024.bwV.entities.User;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.repositories.UsersRepository;
import ep2024.bwV.services.UserRolesService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class RolesAndAdminInitializer {

    @Autowired
    private UserRolesService rolesService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder bcrypt;

@PostConstruct
public void initializeRolesAndAdminUser() {
        try {
            if (rolesService.findByName("ADMIN") == null) {
                rolesService.save(new Role("ADMIN"));
            }
            if (rolesService.findByName("USER") == null) {
                rolesService.save(new Role("USER"));
            }

            if (rolesService == null) {
                System.out.println("rolesService is null");
            } else {
                System.out.println("rolesService is initialized");
            }

            if (usersRepository.findByEmail("testadmin@email.com").isEmpty()) {
                Role adminRole = rolesService.findByName("ADMIN");
                List<Role> roles = List.of(adminRole);
                User adminUser = new User(
                        "testadmin@email.com",
                        bcrypt.encode("12345"),
                        "Admin",
                        "Hammer",
                        "banHammer",
                        "https://ui-avatars.com/api/?name=Admin+Hammer",
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
