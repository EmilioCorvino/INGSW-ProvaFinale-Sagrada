package it.polimi.ingsw.utils.exceptions;

/**
 * This exception manages the particular condition when an illegal placement is intended to be made.
 */
public class IllegalPlacementExceptions extends Exception {

    /**
     * The error message to show;
     */
    private final String message;

    public IllegalPlacementExceptions(String errorMessage) {
        this.message = errorMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
