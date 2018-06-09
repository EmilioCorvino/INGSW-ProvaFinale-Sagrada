package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
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
    public void executeMove(GamePlayManager commonBoard, SetUpInformationUnit setUpInfoUnit) {

    }
}
