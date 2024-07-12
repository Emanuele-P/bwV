package ep2024.bwV.services;

import ep2024.bwV.entities.StatoFattura;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.repositories.StatoFattureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StatoFattureService {
    @Autowired
    private StatoFattureRepository statoFattureRepository;

    public StatoFattura findById(UUID id){
        return statoFattureRepository.findById(id).orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata!"));
    }

    public StatoFattura findOrCreateStato(String stato) {
        Optional<StatoFattura> existingStato = statoFattureRepository.findByStato(stato);
        return existingStato.orElseGet(() -> statoFattureRepository.save(new StatoFattura(stato)));
    }
}
