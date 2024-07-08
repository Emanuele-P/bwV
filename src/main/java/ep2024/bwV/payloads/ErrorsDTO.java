package ep2024.bwV.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime errorTime) {
}
