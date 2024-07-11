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
        System.out.println("Attempting to find role by name: " + name);
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Role not found: " + name));
        System.out.println("Found role: " + role.getName());
        return role;
    }

    public Role save(Role role) {
        System.out.println("Saving role: " + role.getName());
        return roleRepository.save(role);
    }

    public Optional<Role> findById(String name) {
        System.out.println("Attempting to find role by ID: " + name);
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isPresent()) {
            System.out.println("Found role: " + role.get().getName());
        } else {
            System.out.println("Role not found for ID: " + name);
        }
        return role;
    }
}
