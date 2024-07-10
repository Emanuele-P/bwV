package ep2024.bwV.services;

import ep2024.bwV.entities.Fatture;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewFatturaDTO;
import ep2024.bwV.repositories.FattureRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FattureService {
    @Autowired
    private FattureRepositories fattureRepositories;
    public Fatture save(NewFatturaDTO body) {
        this.fattureRepositories.findByNumero(body.numero()).ifPresent(
                user -> {
                    throw new BadRequestException("L'email " + body.numero() + " è già in uso!");
                }
        );

        Fatture newFattura = new Fatture(body.importo(), body.data(), body.numero());

        return fattureRepositories.save(newFattura);
    }

    public Fatture findByNumero(long numero){
        return fattureRepositories.findByNumero(numero).orElseThrow(() -> new NotFoundException("Fattura con numero " + numero + " non trovata!"));
    }
    public Fatture findById(UUID id){
        return fattureRepositories.findById(id).orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata!"));
    }


    public Page<Fatture> getFatture(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return fattureRepositories.findAll(pageable);
    }

 
    public void findByNumAndDelete(long num) {
        Fatture found = this.findByNumero(num);
        this.fattureRepositories.delete(found);
    }


}
