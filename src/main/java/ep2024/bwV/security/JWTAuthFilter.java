package ep2024.bwV.security;

import ep2024.bwV.entities.Admin;
import ep2024.bwV.entities.User;
import ep2024.bwV.exceptions.UnauthorizedException;
import ep2024.bwV.repositories.AdminsRepository;
import ep2024.bwV.repositories.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private AdminsRepository adminRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore inserisci correttamente il token nell'header");
        }

        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
        String id = jwtTools.extractIdFromToken(accessToken);

        Optional<User> currentUser = usersRepository.findById(UUID.fromString(id));
        Optional<Admin> currentAdmin = adminRepository.findById(UUID.fromString(id));

        if (currentUser.isPresent()) {
            User currentAuthorized = currentUser.get();
            setAuthentication(currentAuthorized);
            System.out.println("Authenticated user: " + currentAuthorized);
        } else if (currentAdmin.isPresent()) {
            Admin currentAuthorized = currentAdmin.get();
            setAuthentication(currentAuthorized);
            System.out.println("Authenticated admin: " + currentAuthorized);
        } else {
            throw new UnauthorizedException("User not found.");
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Object currentAuthorized) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentAuthorized, null, ((UserDetails) currentAuthorized).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match("/auth/**", request.getServletPath()) ||
                pathMatcher.match("/provinces/**", request.getServletPath());
    }
}
