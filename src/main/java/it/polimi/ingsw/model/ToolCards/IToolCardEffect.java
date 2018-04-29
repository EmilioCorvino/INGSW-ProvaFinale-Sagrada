package it.polimi.ingsw.model.ToolCards;

/**
 * This interface contains all the useful methods to manage the tool cards and the correspondent effects.
 */
public interface IToolCardEffect {

    /**
     * Checks if a specific tool card can be used.
     * @param currentTurn: the turn used to compare with the turn available for the tool card to be used
     * @return true if the tool card can be used, false otherwise.
     */
    public boolean canBeUsed(int currentTurn);
}
