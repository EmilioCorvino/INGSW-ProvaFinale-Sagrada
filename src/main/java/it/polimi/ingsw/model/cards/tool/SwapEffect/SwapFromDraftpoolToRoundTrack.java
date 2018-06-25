package it.polimi.ingsw.model.cards.tool.SwapEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.RoundTrack;
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

        Die die1 = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice().get(informationUnit.getSourceIndex());
        RoundTrack roundTrack = manager.getControllerMaster().getCommonBoard().getRoundTrack();
        roundTrack.setRoundToBeUpdated(informationUnit.getExtraParam());
        Die die2 = manager.getControllerMaster().getCommonBoard().getRoundTrack().removeDie(informationUnit.getOffset());

        super.swapDice(die1, die2);

        IMove move = new DiePlacementMove();
        move.executeMove(manager, informationUnit);
    }
}