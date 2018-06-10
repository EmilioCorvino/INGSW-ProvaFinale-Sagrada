package it.polimi.ingsw.model.cards.tool;

public interface IToolCard {

    /**
     * Checks if the tool card can be used.
     * @param currentTurn: the turn used to compare with the turn available for the tool card to be used.
     * @return true if the current turn is equal to the turn in which the card can be used.
     */
    boolean canBeUsed(int currentTurn);
}
