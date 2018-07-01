package it.polimi.ingsw.model.cards.tool.valueeffects;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.move.DefaultDiePlacementMove;
import it.polimi.ingsw.model.move.IMove;

/**
 * This class manages the too effect that computes the opposite value of a chosen die.
 */
public class OppositeValueEffect extends AValueEffect {

    /**
     * This is the value used to compute the opposite value of the chosen die.
     */
    public static final int OPPOSITE = 7;

    /**
     * This method computes the opposite value of the chosen die.
     * @param chosenDie the die chosen from the draft pool.
     * @return the die with the modified value.
     */
    public Die computeOppositeValue(Die chosenDie) {
        chosenDie.setActualDieValue(OPPOSITE - chosenDie.getActualDieValue());
        super.checkValue(chosenDie.getActualDieValue());
        return chosenDie;
    }

    /**
     * This method execute the effect of the computation of the opposite value of a chosen die.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {
        DiceDraftPool draftPool = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draftPool.createCopy();

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();
        wp.createCopy();

        Die die = draftPool.getAvailableDiceCopy().get(setUpInfoUnit.getSourceIndex());
        computeOppositeValue(die);

        if(!super.checkExistingCellsToUse(wp, die)) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer("Non ci sono celle disponibili in cui il dado può essere piazzato");
            return;
        }

        super.executeMove(manager, setUpInfoUnit);
    }
}