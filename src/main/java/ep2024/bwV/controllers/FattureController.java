package ep2024.bwV.controllers;

import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.Fattura;
import ep2024.bwV.payloads.NewFatturaDTO;
import ep2024.bwV.repositories.ClienteRepository;
import ep2024.bwV.services.ClienteService;
import ep2024.bwV.services.FattureService;
import ep2024.bwV.services.StatoFattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/invoices")
public class FattureController {

    @Autowired
    private FattureService fattureService;

    @Autowired
    private StatoFattureService statoFattureService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Page<Fattura> getAllFatture(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return fattureService.getFatture(page, size, sortBy);
    }
//
//    @GetMapping
//    public Page<Fattura> getAllFattureByStato(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "stato") String sortBy) {
//        return fattureService.getFatture(page, size, sortBy);
//    }


    //admin user legato id cliente
    @GetMapping("/{fatturenum}")
    public Fattura findByNumero(@PathVariable long numero) {
        return fattureService.findByNumero(numero);
    }

    //admin user legato id cliente
    @GetMapping("/{fatturaId}")
    public Fattura findById(@PathVariable UUID id) {
        return fattureService.findById(id);
    }

    // solo admin legato id cliente
    @PostMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura save(@RequestParam UUID idCliente, @RequestBody @Validated NewFatturaDTO body) {
       Cliente found = clienteService.findById(idCliente);
       return fattureService.save(body);
    }

    // solo admin
    @DeleteMapping("/{fatturanum}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByNumAndDelete(@PathVariable long num) {
        fattureService.findByNumAndDelete(num);
    }

    //update solo admin PUT anche patch, legato anche allo stato

}
