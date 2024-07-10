package ep2024.bwV.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewAdressDTO(

        @NotBlank(message = "Questo campo è obbligatorio")
        @Size(min = 5, max = 30, message = "La via deve essere tra 5 e 30 caratteri.")
        String via,

        @NotBlank(message = "Questo campo è obbligatorio")
        @Size(min = 1, max = 5, message = "Il civico deve essere tra 1 e 5 caratteri.")
        String civico,

        @NotNull(message = "Questo campo è obbligatorio")
        String cap,

        @NotNull(message = "Questo campo è obbligatorio")
        UUID comuneId
) {
}
