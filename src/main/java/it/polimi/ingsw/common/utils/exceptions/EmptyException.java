package it.polimi.ingsw.common.utils.exceptions;

/**
 * This exception is thrown when something is trying to get an object from an empty source.
 * @see it.polimi.ingsw.server.model.cards.objective.AObjectiveCardsDeck
 * @see it.polimi.ingsw.server.model.die.containers.WindowPatternCardDeck
 * @see it.polimi.ingsw.server.model.cards.tool.ToolCardsDeck
 */
public class EmptyException extends Exception {
    private final String message;

    public EmptyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
