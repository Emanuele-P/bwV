package ep2024.bwV.security;

import ep2024.bwV.exceptions.UnauthorizedException;
import ep2024.bwV.repositories.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore inserisci correttamente il token nell'header");
        }

        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
//        String id; //need to fix this part
//        Optional<User> currentUser = usersRepository.findById(UUID.fromString(id));

        filterChain.doFilter(request, response);

    }
        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            return new AntPathMatcher().match("/auth/**", request.getServletPath());
        }
}
