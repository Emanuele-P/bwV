package ep2024.bwV.controllers;

import ep2024.bwV.entities.Fattura;
import ep2024.bwV.entities.User;
import ep2024.bwV.services.FattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private FattureService fattureService;

    //Save user


    //PUT /me, controllo sulla mail


    //GET invoices by user
    @GetMapping("/me/invoices")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<Fattura> getUserInvoices(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return fattureService.findByCliente(currentAuthenticatedUser.getCliente());
    }

    //GETUSER /me per vedere proprie info
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

}
