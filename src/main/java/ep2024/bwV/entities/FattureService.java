package ep2024.bwV.entities;

import exeption.BadRequestException;
import exeption.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    public Fatture save(NewFattureDTO body) {
        this.fattureRepositories.findByNumero(body.numero()).ifPresent(
                user -> {
                    throw new BadRequestException("L'email " + body.numero() + " è già in uso!");
                }
        );

        Fatture newFattura = new Fatture(body.importo(), body.data(), body.numero());

        return fattureRepositories.save(newFattura);
    }

    public Fatture findByNumero(long numero){
        return fattureRepositories.findByNumero(numero).orElseThrow(() -> new NotFoundException("fattura con numero " + numero + " non trovato!"));
    }
    public Fatture findById(UUID id){
        return fattureRepositories.findById(id).orElseThrow(() -> new NotFoundException("fattura con id " + id + " non trovato!"));
    }


    public Page<Fatture> getFatture(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return fattureRepositories.findAll(pageable);
    }

 
    public void findByNumAndDelete(long num) {
        Fatture found = this.findByNumero(num);
        this.fattureRepositories.delete(found);
    }


}
