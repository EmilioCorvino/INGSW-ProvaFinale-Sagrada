package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.move.IMove;

/**
 * The class that manages the common characteristic of the effects of the tool cards.
 */
public abstract class AToolCardEffect implements IMove {


    public boolean checkPlacement() {
        return false;
    }
}
