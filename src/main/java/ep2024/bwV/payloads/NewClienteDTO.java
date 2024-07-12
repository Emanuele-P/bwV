package ep2024.bwV.payloads;

import ep2024.bwV.enums.TipoCliente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record NewClienteDTO(
        @NotEmpty
        String ragioneSociale,
        @NotNull
        int partitaIva,
        @NotEmpty
        String email,
        @NotNull
        LocalDate dataInserimento,
        @NotNull
        LocalDate dataUltimoContatto,

        double fatturatoAnnuale,
        @NotEmpty
        String pec,
        @NotEmpty
        String telefono,
        @NotEmpty
        String emailContatto,
        @NotEmpty
        String nomeContatto,
        @NotEmpty
        String cognomeContatto,
        @NotEmpty
        String telefonoContatto,

        String logoAziendale,

        TipoCliente tipoCliente,

        @NotNull
        UUID indirizzoSedeLegaleId,

        @NotNull
        UUID indirizzoSedeOperativaId

) {
}
