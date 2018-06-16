package it.polimi.ingsw.model.cards.tool.SwapEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;

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

        Die die1 = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice().get(informationUnit.getSourceIndex());
        //Die die2 = manager.getControllerMaster().getCommonBoard().getRoundTrack().getAvailableDice().get()


    }
}
