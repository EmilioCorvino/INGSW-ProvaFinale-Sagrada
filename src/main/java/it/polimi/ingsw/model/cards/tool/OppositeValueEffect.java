package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.ADieContainer;
import it.polimi.ingsw.network.PlayerColor;

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

    @Override
    public void executeMove(PlayerColor currentPlayer, GamePlayManager commonBoard, SetUpInformationUnit informationUnit, ADieContainer source) {

    }
}
