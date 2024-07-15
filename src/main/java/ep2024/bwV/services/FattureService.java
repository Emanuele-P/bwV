package ep2024.bwV.services;

import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.Fattura;
import ep2024.bwV.entities.StatoFattura;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.ChangeStatoFatturaDTO;
import ep2024.bwV.payloads.NewFatturaDTO;
import ep2024.bwV.repositories.ClienteRepository;
import ep2024.bwV.repositories.FattureRepository;
import ep2024.bwV.repositories.StatoFattureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class FattureService {
    @Autowired
    private FattureRepository fattureRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private StatoFattureRepository statoFattureRepository;
    @Autowired
    private ClienteService clienteService;

    public Page<Fattura> getFatture(UUID clienteId, int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        if (clienteId != null) {
            return fattureRepository.findByClienteId(clienteId, pageable);
        } else {
            return fattureRepository.findAll(pageable);
        }
    }

    public Fattura save(NewFatturaDTO body, UUID clienteId) {

        if (body.importo() < 0) {
            throw new IllegalArgumentException("L'importo della fattura non può essere minore di 0!");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente not found"));

        StatoFattura statoFattura = new StatoFattura("generata");
        statoFattura = statoFattureRepository.save(statoFattura);

        Random rnd = new Random();
        long numero = rnd.nextLong(100000000, 999999999);
        Fattura newFattura = new Fattura(body.importo(), body.data(), numero);
        newFattura.setCliente(cliente);
        newFattura.setStato(statoFattura);
        clienteService.updateFatturato(clienteId, newFattura.getImporto());
        return fattureRepository.save(newFattura);
    }

    public Fattura findById(UUID id) {
        return fattureRepository.findById(id).orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata!"));
    }

    public Fattura updateFattura(UUID id, NewFatturaDTO body) {
        Fattura existingFattura = findById(id);
        existingFattura.setImporto(body.importo());
        existingFattura.setData(body.data());

        StatoFattura statoFattura = statoFattureRepository.findByStato(body.stato())
                .orElseGet(() -> statoFattureRepository.save(new StatoFattura(body.stato())));
        existingFattura.setStato(statoFattura);

        return fattureRepository.save(existingFattura);
    }

    public Fattura updateStatoFattura(UUID fatturaId, ChangeStatoFatturaDTO body) {
        Fattura fattura = findById(fatturaId);
        StatoFattura statoFattura = statoFattureRepository.findByStato(body.stato())
                .orElseGet(() -> statoFattureRepository.save(new StatoFattura(body.stato())));
        fattura.setStato(statoFattura);
        return fattureRepository.save(fattura);
    }

    public void deleteFattura(UUID id) {
        Fattura existingFattura = findById(id);
        fattureRepository.delete(existingFattura);
    }

}
