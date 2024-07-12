package ep2024.bwV.services;

import ep2024.bwV.entities.Role;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRolesService {
    @Autowired
    private UserRolesRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Role not found: " + name));
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
