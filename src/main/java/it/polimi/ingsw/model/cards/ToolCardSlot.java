package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.tool.ToolCard;

/**
 * A class that contains a {@link it.polimi.ingsw.model.cards.tool.ToolCard}.
 * Card slots are fetched instead of the card itself, to be sure the card is available in the specific match.
 */
public class ToolCardSlot {

    /**
     * {@link ToolCard} contained in the slot.
     */
    private final ToolCard toolCard;

    /**
     * Cost, in favor tokens, to use the {@link it.polimi.ingsw.model.cards.tool.ToolCard}. If it hasn't been used
     * before, the cost is 1, otherwise will be set to 2.
     */
    private int cost;

    public ToolCardSlot(ToolCard card) {
        this.toolCard = card;
    }

    /**
     * This method checks if a player has enough favor tokens to use the tool card contained in this slot.
     * @param playerTokens the favor tokens to check.
     * @return {@code true} if the condition is satisfied, {@code false} otherwise.
     */
    public boolean canCardBePaid(int playerTokens) {
        return playerTokens >= this.cost;
    }

    /**
     * This method checks if the tool card contained inside implies a die placement.
     * @return true if it implies a die placement, false otherwise.
     */
    public boolean doesCardImplyPlacement() {
        return this.toolCard.impliesPlacement();
    }

    public ToolCard getToolCard() {
        return toolCard;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
