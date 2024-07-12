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
    private double fatturatoAnnuale;

    private String pec;

    private String telefono;

    @Column(name = "email_contatto")
    private String emailContatto;

    @Column(name = "nome_contatto")
    private String nomeContatto;

    @Column(name = "cognome_contatto")
    private String cognomeContatto;

    @Column(name = "telefono_contatto")
    private String telefonoContatto;

    @Column(name = "logo_aziendale")
    private String logoAziendale;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Fattura> fatture;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "indirizzo_sede_legale_id")
    private Indirizzo indirizzoSedeLegale;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "indirizzo_sede_operativa_id")
    private Indirizzo indirizzoSedeOperativa;


    public Cliente(String ragioneSociale, int partitaIva, String email, LocalDate dataInserimento, LocalDate dataUltimoContatto, Long fatturatoAnnuale, String pec, String telefono, String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto, String logoAziendale, Indirizzo indirizzoSedeLegale, Indirizzo indirizzoSedeOperativa, TipoCliente tipoCliente) {
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
        this.logoAziendale = logoAziendale;
        this.indirizzoSedeLegale = indirizzoSedeLegale;
        this.indirizzoSedeOperativa = indirizzoSedeOperativa;
        this.tipoCliente = tipoCliente;
    }

    public void setFatturatoAnnuale(double fatturatoAnnuale) {
        this.fatturatoAnnuale += fatturatoAnnuale;
    }
}
