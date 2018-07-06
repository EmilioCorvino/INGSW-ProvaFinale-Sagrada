package it.polimi.ingsw.server.model.cards.tool.effects.swap;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.server.controller.managers.GamePlayManager;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.die.containers.ADieContainer;
import it.polimi.ingsw.server.model.die.containers.DiceBag;
import it.polimi.ingsw.server.model.die.containers.DiceDraftPool;

import java.util.Random;

/**
 * This class manages the swap effect between a die chosen from the draft pool container and a random
 * die token from the dice bag.
 */
public class SwapFromDraftPoolToDiceBag extends ASwapDieEffect {

    private Die dieExtracted;

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

        swapDie(draftPool, diceBag, setUpInfoUnit);

        diceBag.overwriteOriginal();

        setUpInfoUnit.setColor(this.dieExtracted.getDieColor());
        setUpInfoUnit.setValue(0);
        manager.getControllerMaster().getGameState().getCurrentTurn().incrementDieCount();
        manager.showDraftedDie(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);
    }

    /**
     * This method move a die from a die container to another one, it takes the information about the move form the informationUnit.
     * @param source The die container from where the die is going to be moved.
     * @param destination The die container where the die moves.
     * @param informationUnit The containers of all the information needed for the moving.
     */
    @Override
    protected void swapDie(ADieContainer source, ADieContainer destination, SetUpInformationUnit informationUnit) {

        //move die from draft to dice Bag.
        Die dieChosen = source.removeDieFromCopy(informationUnit.getSourceIndex());
        destination.addDieToCopy(dieChosen);

        //remove a die from dice bag.
        setDieExtracted(destination.removeDieFromCopy(new Random().nextInt(((DiceBag)destination).getAvailableDice().size())));
    }

    private void setDieExtracted(Die dieExtracted) {
        this.dieExtracted = dieExtracted;
    }
}
