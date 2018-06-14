package it.polimi.ingsw.model.cards.tool.SwapEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;


public class SwapFromDraftPoolToDicebag extends ASwapDieEffect {

    /**
     *
     * @param manager
     * @param setUpInfoUnit
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        DiceDraftPool draftPool = manager.getControllerMaster().getCommonBoard().getDraftPool();
        //DiceBag diceBag = manager.getControllerMaster().getCommonBoard().getDiceBag ???;

       // Die extractedDie = diceBag.extractDie()
        // Die from draft pool

        // dicebag.add(die from draftpool

        // draftpool.add(extracted die)

    }
}
