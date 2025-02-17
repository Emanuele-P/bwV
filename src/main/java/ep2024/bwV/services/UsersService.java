package ep2024.bwV.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ep2024.bwV.entities.Role;
import ep2024.bwV.entities.User;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewUtenteDTO;
import ep2024.bwV.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<User> getUsers(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return usersRepository.findAll(pageable);
    }

    public User save(NewUtenteDTO body) {

        usersRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );

        User user = new User();
        user.setEmail(body.email());
        user.setPassword(bcrypt.encode(body.password()));
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setUsername(body.username());
        user.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());

        Role newRole = userRolesService.findByName("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(newRole);
        user.setRoles(roles);
        return usersRepository.save(user);
    }

    public User findById(UUID userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(UUID userId, NewUtenteDTO updatedUser) {
        User found = this.findById(userId);
        found.setEmail(updatedUser.email());
        found.setPassword(bcrypt.encode(updatedUser.password()));
        found.setName(updatedUser.name());
        found.setSurname(updatedUser.surname());
        found.setUsername(updatedUser.username());
        found.setAvatar("https://ui-avatars.com/api/?name=" + updatedUser.name() + "+" + updatedUser.surname());
        return usersRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        usersRepository.delete(found);
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    public String uploadAvatar(MultipartFile file) throws IOException {
        return (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

    public User updateAvatar(UUID userId, String url) {
        User employee = this.findById(userId);
        employee.setAvatar(url);
        return usersRepository.save(employee);
    }
}
