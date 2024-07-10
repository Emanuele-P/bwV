package ep2024.bwV.controllers;

import ep2024.bwV.entities.Utente;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.payloads.NewUserDTO;
import ep2024.bwV.payloads.UserLoginDTO;
import ep2024.bwV.payloads.UserLoginResponseDTO;
import ep2024.bwV.services.AuthService;
import ep2024.bwV.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload) {
        return new UserLoginResponseDTO(authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    private Utente saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            throw new BadRequestException(validationResult.getAllErrors());
        }
        return usersService.save(body);
    }
}
