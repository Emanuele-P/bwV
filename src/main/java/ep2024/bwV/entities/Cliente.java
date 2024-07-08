package ep2024.bwV.entities;


import ep2024.bwV.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter


public class Cliente {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String ragioneSociale;

    private int partitaIva;

    private String email;

    private LocalDate dataInserimento;

    private LocalDateTime dataUltimoContatto;

    private Long fatturatoAnnuale;

    private String pec;

    private int telefono;

    private String emailContatto;

    private String nomeContatto;

    private String cognomeContatto;

    private int telefonoContatto;

    private String logoAziendale;

    private String indirizzo;

    private TipoCliente tipoCliente;
    @OneToOne
    @JoinColumn(name = "utente_email")
    private Utente utente;

    public Cliente(String ragioneSociale, int partitaIva, String email, LocalDate dataInserimento, LocalDateTime dataUltimoContatto, Long fatturatoAnnuale, String pec, int telefono, String emailContatto, String nomeContatto, String cognomeContatto, int telefonoContatto, String indirizzo, TipoCliente tipoCliente, Utente utente) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.indirizzo = indirizzo;
        this.tipoCliente = tipoCliente;
        this.utente = utente;
    }
}
