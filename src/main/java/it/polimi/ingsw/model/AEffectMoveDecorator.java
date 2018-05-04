package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.tool.IToolCard;

/**
 *
 */
public class AEffectMoveDecorator implements IMove {

    protected IMove decoratedMove;


    @Override
    public boolean executeMove() {
        return false;
    }
}
