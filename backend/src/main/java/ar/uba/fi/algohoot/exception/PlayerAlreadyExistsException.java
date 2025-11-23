package ar.uba.fi.algohoot.exception;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
