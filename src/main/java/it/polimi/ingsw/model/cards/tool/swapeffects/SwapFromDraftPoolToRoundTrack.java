package it.polimi.ingsw.model.cards.tool.swapeffects;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.die.containers.RoundTrack;
import it.polimi.ingsw.model.player.Player;

/**
 * This class manages the swap effect between a die chosen from the draft pool container and a die
 * chosen from the round track container.
 */
public class SwapFromDraftPoolToRoundTrack extends ASwapDieEffect {

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
        Die die2 = manager.getControllerMaster().getCommonBoard().getRoundTrack().removeDieFromCopy(informationUnit.getOffset());

        System.out.println(die1.getActualDieValue() + " " + die1.getDieColor());
        System.out.println(die2.getActualDieValue() + " " + die2.getDieColor());
        super.swapDice(die1, die2);
        System.out.println(die1.getActualDieValue() + " " + die1.getDieColor());
        System.out.println(die2.getActualDieValue() + " " + die2.getDieColor());

        Player p = manager.getControllerMaster().getGameState().getCurrentPlayer();
        informationUnit.setColor(die1.getDieColor());
        informationUnit.setValue(die1.getActualDieValue());
        manager.showDraftedDie(p, informationUnit);

        }

}