package ep2024.bwV.services;


import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.Indirizzo;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewClienteDTO;
import ep2024.bwV.repositories.ClienteRepository;
import ep2024.bwV.repositories.IndirizzoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    public Page<Cliente> getClienti(int pageNumber, int pageSize, String sortBy, Specification<Cliente> spec) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return clienteRepository.findAll(spec, pageable);
    }

    public Cliente save(NewClienteDTO body) {

        clienteRepository.findByEmail(body.email()).ifPresent(
                cliente -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );

        clienteRepository.findByPec(body.pec()).ifPresent(
                cliente -> {
                    throw new BadRequestException("La pec " + body.pec() + " è già in uso!");
                }
        );

        Indirizzo sedeLegale = indirizzoRepository.findById(body.indirizzoSedeLegaleId())
                .orElseThrow(() -> new EntityNotFoundException("Indirizzo sede legale not found"));

        Indirizzo sedeOperativa = indirizzoRepository.findById(body.indirizzoSedeOperativaId())
                .orElseThrow(() -> new EntityNotFoundException("Indirizzo sede operativa not found"));

        Cliente newCliente = new Cliente(
                body.ragioneSociale(),
                body.partitaIva(),
                body.email(),
                body.dataInserimento(),
                body.dataUltimoContatto(),
                body.fatturatoAnnuale(),
                body.pec(),
                body.telefono(),
                body.emailContatto(),
                body.nomeContatto(),
                body.cognomeContatto(),
                body.telefonoContatto(),
                "https://ui-avatars.com/api/?name=" + body.nomeContatto() + "+" + body.cognomeContatto(),
                sedeLegale,
                sedeOperativa,
                body.tipoCliente()
        );

        return clienteRepository.save(newCliente);
    }

    public Cliente findById(UUID userId) {
        return this.clienteRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public Cliente findByIdAndUpdate(UUID userId, NewClienteDTO updatedCliente) {
        Cliente found = this.findById(userId);

        found.setRagioneSociale(updatedCliente.ragioneSociale());
        found.setPartitaIva(updatedCliente.partitaIva());
        found.setEmail(updatedCliente.email());
        found.setDataInserimento(updatedCliente.dataInserimento());
        found.setDataUltimoContatto(updatedCliente.dataUltimoContatto());
        found.setFatturatoAnnuale(updatedCliente.fatturatoAnnuale());
        found.setPec(updatedCliente.pec());
        found.setTelefono(updatedCliente.telefono());
        found.setEmailContatto(updatedCliente.emailContatto());
        found.setNomeContatto(updatedCliente.nomeContatto());
        found.setCognomeContatto(updatedCliente.cognomeContatto());
        found.setTelefonoContatto(updatedCliente.telefonoContatto());
        found.setLogoAziendale(updatedCliente.logoAziendale());
        found.setTipoCliente(updatedCliente.tipoCliente());

        Indirizzo sedeLegale = indirizzoRepository.findById(updatedCliente.indirizzoSedeLegaleId())
                .orElseThrow(() -> new EntityNotFoundException("Indirizzo sede legale not found"));

        Indirizzo sedeOperativa = indirizzoRepository.findById(updatedCliente.indirizzoSedeOperativaId())
                .orElseThrow(() -> new EntityNotFoundException("Indirizzo sede operativa not found"));

        found.setIndirizzoSedeLegale(sedeLegale);
        found.setIndirizzoSedeOperativa(sedeOperativa);

        return this.clienteRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        Cliente found = this.findById(userId);
        this.clienteRepository.delete(found);
    }
    public void updateFatturato(UUID clienteId,double importo){
        Cliente cliente = this.findById(clienteId);
        cliente.setFatturatoAnnuale(importo);
        this.clienteRepository.save(cliente);

    }

}
