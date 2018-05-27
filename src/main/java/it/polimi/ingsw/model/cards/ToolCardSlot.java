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

    public ToolCardSlot(IToolCard card) {
        this.toolCard = card;
    }

    public IToolCard getToolCard() {
        return toolCard;
    }
}
