package ep2024.bwV.services;


import ep2024.bwV.entities.Cliente;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewClienteDTO;
import ep2024.bwV.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Page<Cliente> getClienti(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return clienteRepository.findAll(pageable);
    }

//    public Page<Cliente> findByNomeContattoStartingWithIgnoreCase(int pageNumber, int pageSize, String sortBy) {
//        if (pageSize > 20) pageSize = 20;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//        return clienteRepository.findAll(pageable);
//    }
//
//    public Page<Cliente> findByFatturatoAnnuale(int pageNumber, int pageSize, String sortBy) {
//        if (pageSize > 20) pageSize = 20;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//        return clienteRepository.findAll(pageable);
//    }
//
//    public Page<Cliente> findByDataInserimento(int pageNumber, int pageSize, String sortBy) {
//        if (pageSize > 20) pageSize = 20;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//        return clienteRepository.findAll(pageable);
//    }
//
//    public Page<Cliente> findByDataUltimoContatto(int pageNumber, int pageSize, String sortBy) {
//        if (pageSize > 20) pageSize = 20;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//        return clienteRepository.findAll(pageable);
//    }


    public Cliente save(NewClienteDTO body) {

        this.clienteRepository.findByEmail(body.email()).ifPresent(

                user -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );

        this.clienteRepository.findByPec(body.pec()).ifPresent(

                user -> {
                    throw new BadRequestException("La pec " + body.pec() + " è già in uso!");
                }
        );


        Cliente newCliente = new Cliente(body.ragioneSociale(), body.partitaIva(), body.email(), body.dataInserimento(), body.dataUltimoContatto(), body.fatturatoAnnuale(), body.pec(), body.telefono(), body.emailContatto(), body.nomeContatto(), body.cognomeContatto(), body.telefonoContatto(), body.indirizzo(), body.tipoCliente());

        return clienteRepository.save(newCliente);
    }

    public Cliente findById(UUID userId) {
        return this.clienteRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public List <Cliente> findByFatturatoAnnuale(Long fatturatoAnnuale) {
        return clienteRepository.findByFatturatoAnnuale(fatturatoAnnuale);
    }

    public Cliente findByDataInserimento(LocalDate dataInserimento) {
        return clienteRepository.findByDataInserimento(dataInserimento).orElseThrow(() -> new NotFoundException(dataInserimento));
    }

    public Cliente findByDataUltimoContatto(LocalDate dataUltimoContatto) {
        return clienteRepository.findByDataUltimoContatto(dataUltimoContatto).orElseThrow(() -> new NotFoundException(dataUltimoContatto));
    }

    public Cliente findByNomeContattoStartingWithIgnoreCase(String partialName) {
        return clienteRepository.findByNomeContattoStartingWithIgnoreCase(partialName).orElseThrow(() -> new NotFoundException(partialName));
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
        found.setIndirizzo(updatedCliente.indirizzo());
        found.setTipoCliente(updatedCliente.tipoCliente());

        return this.clienteRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        Cliente found = this.findById(userId);
        this.clienteRepository.delete(found);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Il cliente con email " + email + " non è stato trovato!"));
    }

    public Cliente findByPec(String pec) {
        return clienteRepository.findByPec(pec).orElseThrow(() -> new NotFoundException("Il cliente con pec" + pec + " non è stato trovato!"));
    }

    public Cliente findByPartitaIva(int partitaIva) {
        return clienteRepository.findByPartitaIva(partitaIva).orElseThrow(() -> new NotFoundException("Il cliente con partita iva: " + partitaIva + " non è stato trovato!"));
    }
}
