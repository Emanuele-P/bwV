package ep2024.bwV.payloads;

import ep2024.bwV.enums.TipoCliente;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NewClienteDTO(
        @NotEmpty
        String ragioneSociale,
        @NotEmpty
        int partitaIva,
        @NotEmpty
        String email,
        @NotEmpty
        LocalDate dataInserimento,
        @NotEmpty
        LocalDateTime dataUltimoContatto,
        @NotEmpty
        Long fatturatoAnnuale,
        @NotEmpty
        String pec,
        @NotEmpty
        int telefono,
        @NotEmpty
        String emailContatto,
        @NotEmpty
        String nomeContatto,
        @NotEmpty
        String cognomeContatto,
        @NotEmpty
        int telefonoContatto,
        @NotEmpty
        String logoAziendale,
        @NotEmpty
        String indirizzo,
        @NotEmpty
        TipoCliente tipoCliente
) {
}
