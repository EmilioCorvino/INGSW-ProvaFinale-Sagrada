package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.Die;

/**
 *
 */
public class OppositeValueEffect extends AValueEffect {

    public OppositeValueEffect(int offset) {
        super.setOffset(offset);
    }

    /**
     *
     * @param chosenDie
     * @return
     */
    public Die computeOppositeValue(Die chosenDie) {
        chosenDie.setActualDieValue(super.offset - chosenDie.getActualDieValue());
        super.checkValue(chosenDie.getActualDieValue());
        return chosenDie;
    }

    @Override
    public void executeMove(CommonBoard commonBoard, IOController ioController) {
        
    }
}
