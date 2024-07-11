package ep2024.bwV.exceptions;

import java.time.LocalDate;
import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(UUID id) {
        super("Record con id " + id + " non trovato!");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Long message) {
        super("Record con fatturatoAnnuale" + message + " non trovato!");
    }

    public NotFoundException(LocalDate message) {
        super("Record con fatturatoAnnuale" + message + " non trovato!");
    }


}
