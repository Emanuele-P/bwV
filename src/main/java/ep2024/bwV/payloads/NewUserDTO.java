package ep2024.bwV.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record NewUserDTO(
        @NotEmpty(message = "Il nome proprio è un dato obbligatorio!")

        String name,
        @NotEmpty(message = "Il cognome è un dato obbligatorio!")

        String surname,
        @NotEmpty(message = "L'email è un dato obbligatorio!")
        @Email(message = "L'email inserita non è valida!")
        String email,
        @NotEmpty(message = "La password è un dato obbligatorio!")

        String password,
        @NotEmpty
        String username
) {
}
