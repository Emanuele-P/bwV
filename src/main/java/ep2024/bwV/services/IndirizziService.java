package ep2024.bwV.services;

import ep2024.bwV.entities.Comune;
import ep2024.bwV.entities.Indirizzo;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewAdressDTO;
import ep2024.bwV.repositories.ComuneRepository;
import ep2024.bwV.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IndirizziService {
    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    public Indirizzo saveAddress(NewAdressDTO body) {
        Comune comune = comuneRepository.findById(body.comuneId())
                .orElseThrow(() -> new NotFoundException("Comune with id " + body.comuneId() + " not found"));

        String nomeComune = comune.getNome();
        String nomeProvincia = comune.getProvincia().getNome();

        Indirizzo indirizzo = new Indirizzo(body.via(), body.civico(), nomeProvincia, body.cap(), comune);
        indirizzo.setNomeComune(nomeComune);
        return indirizzoRepository.save(indirizzo);
    }

    public Indirizzo updateAddress(UUID id, NewAdressDTO body) {
        Indirizzo existingAddress = indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address with id " + id + " not found"));

        Comune comune = comuneRepository.findById(body.comuneId())
                .orElseThrow(() -> new NotFoundException("Comune with id " + body.comuneId() + " not found"));

        String nomeComune = comune.getNome();
        String nomeProvincia = comune.getProvincia().getNome();

        existingAddress.setVia(body.via());
        existingAddress.setCivico(body.civico());
        existingAddress.setLocalita(nomeProvincia);
        existingAddress.setCap(body.cap());
        existingAddress.setComune(comune);
        existingAddress.setNomeComune(nomeComune);

        return indirizzoRepository.save(existingAddress);
    }

    public void deleteAddress(UUID id) {
        Indirizzo existingAddress = indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address with id " + id + " not found"));
        indirizzoRepository.delete(existingAddress);
    }

    public List<Indirizzo> findAllAddresses() {
        return indirizzoRepository.findAll();
    }

    public Indirizzo findById(UUID id) {
        return indirizzoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address with id " + id + " not found"));
    }
}
