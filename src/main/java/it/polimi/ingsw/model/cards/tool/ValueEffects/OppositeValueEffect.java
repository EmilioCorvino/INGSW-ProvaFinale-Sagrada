package it.polimi.ingsw.model.cards.tool.ValueEffects;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;

/**
 *
 */
public class OppositeValueEffect extends AValueEffect {

    /**
     *
     */
    public static final int OPPOSITE = 7;

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

    /**
     *
     * @param manager
     * @param setUpInfoUnit
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {



    }
}
