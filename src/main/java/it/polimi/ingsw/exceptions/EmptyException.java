package it.polimi.ingsw.exceptions;

/**
 * This exception is thrown when something is trying to get an object from an empty source.
 * @see it.polimi.ingsw.model.cards.objective.AObjectiveCardsDeck
 */
public class EmptyException extends Exception{
    private final String message;

    public EmptyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
