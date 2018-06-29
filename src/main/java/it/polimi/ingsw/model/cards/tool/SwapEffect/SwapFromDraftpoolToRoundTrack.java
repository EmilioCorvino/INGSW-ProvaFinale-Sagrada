package it.polimi.ingsw.model.cards.tool.SwapEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.die.containers.RoundTrack;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;

/**
 * This class manages the swap effect between a die chosen from the draft pool container and a die
 * chosen from the round track container.
 */
public class SwapFromDraftpoolToRoundTrack extends ASwapDieEffect {

    /**
     * This method executes the swap and then, if possible, places the die in the window pattern card according to
     * user's coordinates.
     * @param manager the controller.
     * @param informationUnit the info to use.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit informationUnit) {

        RoundTrack roundTrack = manager.getControllerMaster().getCommonBoard().getRoundTrack();
        roundTrack.createCopy();

        if(manager.getControllerMaster().getGameState().getActualRound() == 1) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer("Il Tracciato dei Round è vuoto!");
            return;
        }

        if(roundTrack.getAvailableDiceCopy().get(informationUnit.getExtraParam()).size() == 0) {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer("Non ci sono dadi sulla round selezionato!");
            return;
        }

        DiceDraftPool draftPool = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draftPool.createCopy();
        Die die1 = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice().get(informationUnit.getSourceIndex());
        roundTrack.setRoundToBeUpdated(informationUnit.getExtraParam());
        Die die2 = manager.getControllerMaster().getCommonBoard().getRoundTrack().removeDie(informationUnit.getOffset());

        super.swapDice(die1, die2);

        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();

        if(super.checkExistingCellsToUse(wp, die1)) {
            informationUnit.setColor(die1.getDieColor());
            informationUnit.setValue(die1.getActualDieValue());
            IMove move = new DiePlacementMove();
            move.executeMove(manager, informationUnit);
        } else {
            manager.setMoveLegal(false);
            manager.sendNotificationToCurrentPlayer("Non esistono celle della tua mappa in cui poter piazzare il dado");
        }
    }
}