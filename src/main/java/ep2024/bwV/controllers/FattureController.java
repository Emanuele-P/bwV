package ep2024.bwV.controllers;

import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.Fattura;
import ep2024.bwV.payloads.ChangeStatoFatturaDTO;
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
    public Page<Fattura> getAllFatture(@RequestParam(required = false) UUID customerId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        return fattureService.getFatture(customerId, page, size, sortBy);
    }

    // save providing idCliente
    @PostMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura save(@PathVariable UUID idCliente, @RequestBody @Validated NewFatturaDTO body) {
        return fattureService.save(body, idCliente);
    }

    //get by id
    @GetMapping("/{fatturaId}")
    public Fattura findById(@PathVariable UUID id) {
        return fattureService.findById(id);
    }

    //update
    @PutMapping("/{fatturaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura updateFattura(@PathVariable UUID fatturaId, @RequestBody @Validated NewFatturaDTO body) {
        return fattureService.updateFattura(fatturaId, body);
    }

    // Update statoFattura by fatturaId
    @PatchMapping("/{fatturaId}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura updateStatoFattura(@PathVariable UUID fatturaId,
                                      @RequestBody @Validated ChangeStatoFatturaDTO newStato) {
        return fattureService.updateStatoFattura(fatturaId, newStato);
    }

    //delete
    @DeleteMapping("/{fatturaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFattura(@PathVariable UUID fatturaId) {
        fattureService.deleteFattura(fatturaId);
    }
}
