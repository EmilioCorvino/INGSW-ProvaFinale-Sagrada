package it.polimi.ingsw.model.cards.tool.effects;

import it.polimi.ingsw.model.move.AMove;

/**
 * The class that manages the common characteristic of the effects of the tool cards.
 */
public abstract class AToolCardEffect extends AMove {

    public boolean checkPlacement() {
        return false;
    }
}
