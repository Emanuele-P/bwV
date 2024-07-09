package ep2024.bwV.controllers;

import ep2024.bwV.entities.Comune;
import ep2024.bwV.entities.Indirizzo;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewAdressDTO;
import ep2024.bwV.repositories.ComuneRepository;
import ep2024.bwV.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
public class IndirizziController {
    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @PostMapping
    public Indirizzo saveAddress(@RequestBody NewAdressDTO body) {
        Comune comune = comuneRepository.findById(body.comuneId())
                .orElseThrow(() -> new NotFoundException("Comune with id " + body.comuneId() + " not found"));

        String nomeComune = comune.getNome();
        String nomeProvincia = comune.getProvincia().getNome();

        Indirizzo indirizzo = new Indirizzo(body.via(), body.civico(), nomeProvincia, body.cap(), comune);
        indirizzo.setNomeComune(nomeComune);
        return indirizzoRepository.save(indirizzo);
    }
}
