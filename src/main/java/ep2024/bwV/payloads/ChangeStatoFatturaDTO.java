package ep2024.bwV.payloads;

import jakarta.validation.constraints.NotEmpty;

public record ChangeStatoFatturaDTO(
        @NotEmpty
        String stato
) {
}
