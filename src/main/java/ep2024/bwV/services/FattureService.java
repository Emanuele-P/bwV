package ep2024.bwV.services;

import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.Fattura;
import ep2024.bwV.entities.StatoFattura;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewFatturaDTO;
import ep2024.bwV.repositories.ClienteRepository;
import ep2024.bwV.repositories.FattureRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FattureService {
    @Autowired
    private FattureRepositories fattureRepositories;
    @Autowired
    private ClienteRepository clienteRepository;

    public Fattura save(NewFatturaDTO body) {
        this.fattureRepositories.findByNumero(body.numero()).ifPresent(
                user -> {
                    throw new BadRequestException("L'email " + body.numero() + " è già in uso!");
                }
        );

        Fattura newFattura = new Fattura(body.importo(), body.data(), body.numero());

        return fattureRepositories.save(newFattura);
    }

    public Fattura findByNumero(long numero){
        return fattureRepositories.findByNumero(numero).orElseThrow(() -> new NotFoundException("Fattura con numero " + numero + " non trovata!"));
    }
    public Fattura findById(UUID id){
        return fattureRepositories.findById(id).orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata!"));
    }
    public boolean getStatoFattura(UUID fatturaId) {
        StatoFattura stato = fattureRepositories.findStatoByFatturaId(fatturaId);
        if (stato == null) {
            throw new NotFoundException("Stato della fattura non trovato");
        }
        return stato.isCaricato();
    }

    public Page<Fattura> getFatture(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return fattureRepositories.findAll(pageable);
    }
    public List<Fattura> findByCliente(Cliente cliente){
        return  fattureRepositories.findByCliente(cliente);
    }

    public double getFatturatoAnnuale(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<Fattura> fatture = fattureRepositories.findByCliente(cliente);
        double somma = 0.0;
        for (Fattura fattura : fatture) {
            somma += fattura.getImporto();
        }
        return somma;
    }

 
    public void findByNumAndDelete(long num) {
        Fattura found = this.findByNumero(num);
        this.fattureRepositories.delete(found);
    }


}
