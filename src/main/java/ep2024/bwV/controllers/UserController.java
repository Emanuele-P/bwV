package ep2024.bwV.controllers;

import ep2024.bwV.entities.Role;
import ep2024.bwV.entities.User;
import ep2024.bwV.payloads.NewUtenteDTO;
import ep2024.bwV.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    //save solo da admin prob edit controller
    @GetMapping("/me")
    public User getMyProfile(@AuthenticationPrincipal User loggedUser) {
        return usersService.findById(loggedUser.getId());
    }

    //get all solo admin

    @GetMapping
   @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return usersService.getUsers(page, size, sortBy);
    }

    //update users admin

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findByIdAndUpdate(@PathVariable UUID id, @RequestBody NewUtenteDTO body) {
        return usersService.findByIdAndUpdate(id, body);
    }


    //delete user admin solo se user
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
   @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID id) {
        usersService.findByIdAndDelete(id);
    }
}

