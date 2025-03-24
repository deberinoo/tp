package seedu.address.model.exceptions;

/**
 * Signals that the operation is unable to find a previous model state.
 */
public class NoPreviousModelStateException extends RuntimeException {
    public NoPreviousModelStateException() {
        super("No previous model state found");
    }
}
