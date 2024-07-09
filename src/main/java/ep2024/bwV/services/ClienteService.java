package ep2024.bwV.services;


import ep2024.bwV.entities.Cliente;
import ep2024.bwV.entities.User;
import ep2024.bwV.exceptions.BadRequestException;
import ep2024.bwV.exceptions.NotFoundException;
import ep2024.bwV.payloads.NewClienteDTO;
import ep2024.bwV.payloads.NewUserDTO;
import ep2024.bwV.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Page<Cliente> getUsers(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return clienteRepository.findAll(pageable);
    }

    public Cliente save(NewClienteDTO body) {

        this.clienteRepository.findByEmail(body.email()).ifPresent(

                user -> {
                    throw new BadRequestException("L'email " + body.email() + " è già in uso!");
                }
        );


        Cliente newCliente = new Cliente(body.ragioneSociale(), body.partitaIva(), body.email(), body.dataInserimento(), body.dataUltimoContatto(), body.fatturatoAnnuale(), body.pec(), body.telefono(), body.emailContatto(), body.nomeContatto(), );




        return clienteRepository.save(newCliente);
    }

    public Cliente findById(UUID userId) {
        return this.clienteRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public Cliente findByIdAndUpdate(UUID userId, User modifiedUser) {
        Cliente found = this.findById(userId);

        return this.clienteRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        Cliente found = this.findById(userId);
        this.clienteRepository.delete(found);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
}
