package ep2024.bwV.payloads;

import ep2024.bwV.enums.TipoCliente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewClienteDTO(
        @NotEmpty
        String ragioneSociale,
        @NotNull
        int partitaIva,
        @NotEmpty
        String email,
        @NotEmpty
        LocalDate dataInserimento,
        @NotEmpty
        LocalDate dataUltimoContatto,
        @NotNull
        Long fatturatoAnnuale,
        @NotEmpty
        String pec,
        @NotNull
        int telefono,
        @NotEmpty
        String emailContatto,
        @NotEmpty
        String nomeContatto,
        @NotEmpty
        String cognomeContatto,
        @NotNull
        int telefonoContatto,
        @NotEmpty
        String logoAziendale,
        @NotEmpty
        String indirizzo,

        TipoCliente tipoCliente
) {
}
