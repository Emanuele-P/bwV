package ep2024.bwV.controllers;

import ep2024.bwV.entities.Indirizzo;
import ep2024.bwV.payloads.NewAdressDTO;
import ep2024.bwV.services.IndirizziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class IndirizziController {

    @Autowired
    private IndirizziService indirizziService;

    //SAVE
    @PostMapping
    public Indirizzo saveAddress(@RequestBody NewAdressDTO body) {
        return indirizziService.saveAddress(body);
    }

    //UPDATE
    @PutMapping("/{id}")
    public Indirizzo updateAddress(@PathVariable UUID id, @RequestBody NewAdressDTO body) {
        return indirizziService.updateAddress(id, body);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable UUID id) {
        indirizziService.deleteAddress(id);
    }

    //Find ALL
    @GetMapping
    public List<Indirizzo> findAllAddresses() {
        return indirizziService.findAllAddresses();
    }

    //Find specific
    @GetMapping("/{id}")
    public Indirizzo findAddressById(@PathVariable UUID id) {
        return indirizziService.findById(id);
    }
}
