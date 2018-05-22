package it.polimi.ingsw.exceptions;

public class TooManyUsersException extends Exception {
    private final String message;

    public TooManyUsersException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
