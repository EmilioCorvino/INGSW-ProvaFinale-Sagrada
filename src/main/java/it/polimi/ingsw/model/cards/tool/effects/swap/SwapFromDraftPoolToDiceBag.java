package it.polimi.ingsw.model.cards.tool.effects.swap;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceBag;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;

import java.util.Random;


public class SwapFromDraftPoolToDiceBag extends ASwapDieEffect {

    /**
     * This method manages the placement of the changing it with a die extract random from the dice bag.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {
        manager.incrementEffectCounter();

        DiceDraftPool draftPool = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draftPool.createCopy();

        DiceBag diceBag = manager.getControllerMaster().getCommonBoard().getDraftPool().getDiceBag();
        diceBag.createCopy();

        Die dieChosen = draftPool.removeDieFromCopy(setUpInfoUnit.getSourceIndex());
        diceBag.addDieToCopy(dieChosen);

        Die dieExtracted = diceBag.removeDieFromCopy(new Random().nextInt(diceBag.getAvailableDice().size()));

        setUpInfoUnit.setColor(dieExtracted.getDieColor());
        setUpInfoUnit.setValue(0);
        manager.showDraftedDie(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);

    }
}
