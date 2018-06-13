package it.polimi.ingsw.utils.exceptions;

public class DieNotContainedException extends Exception {

    private final String message;

    public DieNotContainedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
