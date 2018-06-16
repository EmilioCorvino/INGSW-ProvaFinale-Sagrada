package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.move.AEffectMoveDecorator;

/**
 * The class that manages the common characteristic of the effects of the tool cards.
 */
public abstract class AToolCardEffect extends AEffectMoveDecorator implements IToolCardEffect {


    public boolean checkPlacement() {
        return false;
    }




}
