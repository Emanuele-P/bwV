package ep2024.bwV.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NewFatturaDTO(
        @NotEmpty
        LocalDateTime data,
        @NotNull
        long numero,
        @NotNull
        double importo) {
}
