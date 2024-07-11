package ep2024.bwV.services;

import ep2024.bwV.entities.User;
import ep2024.bwV.exceptions.UnauthorizedException;
import ep2024.bwV.payloads.UserLoginDTO;
import ep2024.bwV.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;


    public String authenticateUserAndGenerateToken(UserLoginDTO payload){

        User user = this.usersService.findByEmail(payload.email());
        if(bcrypt.matches(payload.password(), user.getPassword())){
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Your credentials are incorrect!");
        }
    }
}
