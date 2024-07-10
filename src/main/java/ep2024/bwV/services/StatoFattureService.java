package ep2024.bwV.services;

import ep2024.bwV.entities.StatoFattura;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.repositories.StatoFattureRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatoFattureService {
    @Autowired
    private StatoFattureRepositories statoFattureRepositories;
    public StatoFattura findById(UUID id){
        return statoFattureRepositories.findById(id).orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata!"));
    }

}
