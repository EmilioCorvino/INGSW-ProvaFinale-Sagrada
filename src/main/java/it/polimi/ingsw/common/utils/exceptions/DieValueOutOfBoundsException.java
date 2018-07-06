package it.polimi.ingsw.common.utils.exceptions;

public class DieValueOutOfBoundsException extends Exception {
    private final String message;

    public DieValueOutOfBoundsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
