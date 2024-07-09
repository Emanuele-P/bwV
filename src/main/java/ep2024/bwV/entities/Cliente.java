package ep2024.bwV.entities;


import ep2024.bwV.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    private LocalDate dataUltimoContatto;

    private Long fatturatoAnnuale;

    private String pec;

    private int telefono;

    private String emailContatto;

    private String nomeContatto;

    private String cognomeContatto;

    private int telefonoContatto;

    private String logoAziendale;

    private String indirizzo;
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;
    @OneToOne
    @JoinColumn(name = "user_email")
    private User user;

    public Cliente(String ragioneSociale, int partitaIva, String email, LocalDate dataInserimento, LocalDate dataUltimoContatto, Long fatturatoAnnuale, String pec, int telefono, String emailContatto, String nomeContatto, String cognomeContatto, int telefonoContatto, String indirizzo, TipoCliente tipoCliente) {
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

    }
}
