package ep2024.bwV.controllers;

import ep2024.bwV.entities.Fatture;
import ep2024.bwV.payloads.NewFatturaDTO;
import ep2024.bwV.services.FattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/fatture")
public class FattureController {
    @Autowired
    private FattureService fattureService;
    @GetMapping
    public Page<Fatture> getAllFatture(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return fattureService.getFatture(page, size, sortBy);
    }
//    @GetMapping("/{fatturestato}")
//    public Fatture findByStato(@PathVariable boolean stato) {
//        return fattureService.findByStato(stato);
//    }

    @GetMapping("/{fatturenum}")
    public Fatture findByNumero(@PathVariable long numero) {
        return fattureService.findByNumero(numero);
    }

    @GetMapping("/{fatturaId}")
    public Fatture findById(@PathVariable UUID id) {
        return fattureService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fatture save(@RequestBody @Validated NewFatturaDTO body){
        return fattureService.save(body);
}

    @DeleteMapping("/{fatturanum}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByNumAndDelete(@PathVariable long num) {
        fattureService.findByNumAndDelete(num);
    }

}
