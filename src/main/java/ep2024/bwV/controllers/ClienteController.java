package ep2024.bwV.controllers;


import ep2024.bwV.entities.Cliente;
import ep2024.bwV.payloads.NewClienteDTO;
import ep2024.bwV.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //save
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente saveAddress(@RequestBody NewClienteDTO body) {
        return clienteService.save(body);
    }

    //update
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente findByIdAndUpdate(@PathVariable UUID id, @RequestBody NewClienteDTO body) {
        return clienteService.findByIdAndUpdate(id, body);
    }

    //delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID id) {
        clienteService.findByIdAndDelete(id);
    }

    //Get all
    @GetMapping
    public Page<Cliente> getAllClienti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return clienteService.getClienti(page, size, sortBy);
    }

    //find by id
    @GetMapping("/{userId}")
    public Cliente findById(@PathVariable UUID userId) {
        return clienteService.findById(userId);
    }
}
