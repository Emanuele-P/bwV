package ep2024.bwV.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewAdminDTO(
        @NotEmpty(message = "Il nome proprio è un dato obbligatorio!")
        String name,
        @NotEmpty(message = "Il cognome è un dato obbligatorio!")
        String surname,
        @NotEmpty(message = "L'email è un dato obbligatorio!")
        String email,
        @NotEmpty(message = "La password è un dato obbligatorio!")
        String password,
        @NotEmpty(message = "Il username è un dato obbligatorio!")
        String username
) {
}
