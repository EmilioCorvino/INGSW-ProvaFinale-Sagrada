package it.polimi.ingsw.exceptions;

public class UserNameAlreadyTakenException extends Exception {
    private final String message;

    public UserNameAlreadyTakenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
