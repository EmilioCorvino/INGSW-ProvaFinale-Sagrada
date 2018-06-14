package it.polimi.ingsw.model.cards.tool.SwapEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.model.die.diecontainers.RoundTrack;

/**
 *
 */
public class SwapFromDraftpoolToRoundTrack extends ASwapDieEffect {

    /**
     *
     * @param manager
     * @param informationUnit
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit informationUnit) {

        DiceDraftPool draftPool = manager.getControllerMaster().getCommonBoard().getDraftPool();
        RoundTrack roundTrack = manager.getControllerMaster().getCommonBoard().getRoundTrack();

        Die extractedDie = draftPool.removeDie(informationUnit.getSourceIndex());

        roundTrack.setRoundToBeUpdated(manager.getControllerMaster().getGameState().getActualRound());

        roundTrack.addDie(extractedDie);
    }
}
