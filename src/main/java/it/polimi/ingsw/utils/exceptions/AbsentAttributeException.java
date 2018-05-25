package it.polimi.ingsw.utils.exceptions;

/**
 * This exception is thrown if a method cannot fetch an attribute from an object, which should have been set.
 * @see it.polimi.ingsw.model.cards.objective.publics.strategies.SetStrategy
 */
public class AbsentAttributeException extends RuntimeException {
    private final String message;

    public AbsentAttributeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
