package ep2024.bwV.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewFatturaDTO(
        @NotNull
        LocalDate data,
        @NotNull
        double importo,
        String stato
) {
}
