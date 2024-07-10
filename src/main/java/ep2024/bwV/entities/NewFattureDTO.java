package ep2024.bwV.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record NewFattureDTO(
        @NotEmpty
        LocalDateTime data,
        @NotNull
        long numero,
        @NotNull
        double importo) {
}
