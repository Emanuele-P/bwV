package ep2024.bwV.controllers;

import ep2024.bwV.entities.Admin;
import ep2024.bwV.entities.Utente;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewUtenteDTO;
import ep2024.bwV.payloads.UserLoginDTO;
import ep2024.bwV.payloads.UserLoginResponseDTO;
import ep2024.bwV.security.JWTTools;
import ep2024.bwV.services.AdminService;
import ep2024.bwV.services.AuthService;
import ep2024.bwV.services.UsersService;
import ep2024.bwV.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JWTTools jwtTools;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload) {
        String token = authService.authenticateUserAndGenerateToken(payload);
        String id = jwtTools.extractIdFromToken(token);
        Utente found = utenteService.findById(UUID.fromString(id));

        if (found == null) {
            throw new NotFoundException("User not found");
        }

        if (found.getRuolo().equalsIgnoreCase("admin")) {
            return new UserLoginResponseDTO(token, "Welcome Admin " + payload.email());
        } else {
            return new UserLoginResponseDTO(token, "Welcome User " + payload.email());
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private Utente saveUser(@RequestBody @Validated NewUtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return usersService.save(body);
    }

    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin registerAdmin(@RequestBody @Validated NewUtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return adminService.save(body);
    }
}
