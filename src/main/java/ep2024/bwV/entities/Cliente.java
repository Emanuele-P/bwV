package ep2024.bwV.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ep2024.bwV.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clienti")
@NoArgsConstructor
@ToString
@Getter
@Setter


public class Cliente {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "ragione_sociale")
    private String ragioneSociale;
    @Column(name = "partita_iva")
    private int partitaIva;

    private String email;

    @Column(name = "data_inserimento")
    private LocalDate dataInserimento;

    @Column(name = "data_ultimo_contatto")
    private LocalDate dataUltimoContatto;

    @Column(name = "fatturato_annuale")
    private Long fatturatoAnnuale;

    private String pec;

    private int telefono;

    @Column(name = "email_contatto")
    private String emailContatto;

    @Column(name = "nome_contatto")
    private String nomeContatto;

    @Column(name = "cognome_contatto")
    private String cognomeContatto;

    @Column(name = "telefono_contatto")
    private int telefonoContatto;

    @Column(name = "logo_aziendale")
    private String logoAziendale;

    private String indirizzo;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @OneToOne
    @JoinColumn(name = "user_email")
    private User user;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Fatture> fatture;


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
