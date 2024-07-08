package ep2024.bwV.payloads;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(
        @NotEmpty(message = "L'email è obbligatoria!")
        String email,
        @NotEmpty(message = "La password è obbligatoria!")
        String password
) {
}
