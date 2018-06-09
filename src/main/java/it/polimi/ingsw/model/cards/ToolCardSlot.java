package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.AContainer;
import it.polimi.ingsw.model.cards.tool.IToolCard;

/**
 * A class that contains a {@link it.polimi.ingsw.model.cards.tool.IToolCard}.
 * Card slots are fetched instead of the card itself, to be sure the card is available in the specific match.
 */
public class ToolCardSlot extends AContainer {

    /**
     * {@link IToolCard} contained in the slot.
     */
    private final IToolCard toolCard;

    /**
     * Cost, in favor tokens, to use the {@link it.polimi.ingsw.model.cards.tool.ToolCard}. If it hasn't been used
     * before, the cost is 1, otherwise will be set to 2.
     */
    private int cost;

    public ToolCardSlot(IToolCard card) {
        this.toolCard = card;
    }

    /**
     * This method checks if a player has enough favor tokens to use the tool card contained in this slot.
     * @param playerTokens the favor tokens to check.
     * @return true if the condition is satisfied, false otherwise.
     */
    public boolean checkTokens(int playerTokens) {
        return playerTokens >= this.cost;
    }

    /**
     * TODO
     * This method checks if the tool card contained inside implies a die placement.
     * @return true if it implies a die placement, false otherwise.
     */
    public boolean checkImpliesPlacement() {
        return false;
    }

    public IToolCard getToolCard() {
        return toolCard;
    }

    public int getCost() {
        return cost;
    }


}
