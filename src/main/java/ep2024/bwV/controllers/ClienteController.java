package ep2024.bwV.controllers;


import ep2024.bwV.entities.Cliente;
import ep2024.bwV.payloads.NewClienteDTO;
import ep2024.bwV.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //GETALL fatta solo da admin
    public Page<Cliente> getAllClienti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return clienteService.getUsers(page, size, sortBy);
    }

    //save che sarà fatta solo da admin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveAddress(@RequestBody NewClienteDTO body) {
        return clienteService.save(body);
    }

    //FINDBYID fatta solo da admin

    @GetMapping("/{userId}")
    public Cliente findById(@PathVariable UUID userId) {
        return clienteService.findById(userId);
    }

    //FINDBYEMAIL fatta solo da admin
    @GetMapping("/by-email")
    public Cliente findByEmail(@PathVariable String email) {
        return clienteService.findByEmail(email);
    }


    //FINDBYPIVA fatta solo da admin
    @GetMapping("/by-vatNumber")
    public Cliente findByPartitaIva(@PathVariable int partitaIva) {
        return clienteService.findByPartitaIva(partitaIva);
    }

    //update fatta da admin
    @PutMapping("/{id}")
    public Cliente findByIdAndUpdate(@PathVariable UUID id, @RequestBody NewClienteDTO body) {
        return clienteService.findByIdAndUpdate(id, body);
    }

    //delete fatta da admin
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        clienteService.findByIdAndDelete(id);
    }
}
