package ep2024.bwV.services;

import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.Fattura;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewClienteDTO;
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
    @Autowired
    private ClienteService clienteService;

    public Page<Fattura> getFatture(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return fattureRepositories.findAll(pageable);
    }

    public Fattura save(NewFatturaDTO body, UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new NotFoundException("Cliente not found"));

        fattureRepositories.findByNumero(body.numero()).ifPresent(
                user -> {
                    throw new BadRequestException("Il numero della fattura " + body.numero() + " è giá in uso!");
                }
        );

        Fattura newFattura = new Fattura(body.importo(), body.data(), body.numero());
        newFattura.setCliente(cliente);
        clienteService.updateFatturato(clienteId,newFattura.getImporto());


        return fattureRepositories.save(newFattura);
    }

    public Fattura findByNumero(long numero) {
        return fattureRepositories.findByNumero(numero).orElseThrow(() -> new NotFoundException("Fattura con numero " + numero + " non trovata!"));
    }

    public Fattura findById(UUID id) {
        return fattureRepositories.findById(id).orElseThrow(() -> new NotFoundException("Fattura con id " + id + " non trovata!"));
    }

    public Fattura updateFattura(UUID id, NewFatturaDTO body) {
        Fattura existingFattura = findById(id);
        existingFattura.setImporto(body.importo());
        existingFattura.setData(body.data());
        existingFattura.setNumero(body.numero());
        return fattureRepositories.save(existingFattura);
    }

    public void deleteFattura(UUID id) {
        Fattura existingFattura = findById(id);
        fattureRepositories.delete(existingFattura);
    }


//    public boolean getStatoFattura(UUID fatturaId) {
//        StatoFattura stato = fattureRepositories.findStatoByFatturaId(fatturaId);
//        if (stato == null) {
//            throw new NotFoundException("Stato della fattura non trovato");
//        }
//        return stato.isCaricato();
//    }

//    public Page<Fattura> findByStato(int pageNumber, int pageSize, String sortBy) {
//        if (pageSize > 50) pageSize = 50;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//        return fattureRepositories.findAll(pageable);
//    }
//
//    public Page<Fattura> getFattureByData(int pageNumber, int pageSize, String sortBy) {
//        if (pageSize > 50) pageSize = 50;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//        return fattureRepositories.findAll(pageable);
//    }
//
//    public Page<Fattura> getFattureByCliente(int pageNumber, int pageSize, String sortBy) {
//        if (pageSize > 50) pageSize = 50;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//        return fattureRepositories.findAll(pageable);
//    }
//
//    public List<Fattura> findByCliente(Cliente cliente) {
//        return fattureRepositories.findByCliente(cliente);
//    }

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
}
