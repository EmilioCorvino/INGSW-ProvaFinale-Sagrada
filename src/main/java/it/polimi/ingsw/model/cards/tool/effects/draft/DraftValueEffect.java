package it.polimi.ingsw.model.cards.tool.effects.draft;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.effects.AToolCardEffect;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;

import java.util.List;
import java.util.Random;

/**
 * This class manages the effect of the tool card that allow the user to relaunch the chosen die.
 */
public class DraftValueEffect extends AToolCardEffect {

    /**
     * This attribute is the number of dice that has to be drafted. If is equal to 0 means that all dice of the draft pool must be rolled.
     */
    private int limit;

    /**
     * Construct this effect with a specific value for limit.
     * @param limit the limit parameter to set.
     */
    public DraftValueEffect(int limit) {
        this.limit = limit;
    }

    /**
     * This method computes a random value for the chosen die.
     * @param chosenDie the die the player wants to draft.
     * @return the die with the new value.
     */
    private Die computeRandomDieValue(Die chosenDie) {
        Random randomNumber = new Random();
        chosenDie.setActualDieValue(randomNumber.nextInt(6) + 1);
        return chosenDie;
    }

    /**
     * This method manages the placement of the chosen die with the new computed random value.
     * @param manager part of the controller that deals with the game play.
     * @param setUpInfoUnit object containing all the information needed to perform the move.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        manager.setMoveLegal(true);
        DiceDraftPool draft = manager.getControllerMaster().getCommonBoard().getDraftPool();
        draft.createCopy();
        List<Die> diceToDraft = draft.getAvailableDiceCopy();
        manager.incrementEffectCounter();

        if(this.limit == 0) {
            if (manager.getControllerMaster().getGameState().getCurrentTurn().isDiePlaced()) {
                manager.setMoveLegal(false);
                manager.sendNotificationToCurrentPlayer("\nNon puoi utilizzare questa tool dopo aver piazzato un dado." + COMMANDS_HELP);
            }
            for(int i=0; i< diceToDraft.size(); i++) {
                Die dieToCompute = computeRandomDieValue(diceToDraft.get(i));
                diceToDraft.set(i, dieToCompute);
            }
            manager.showUpdatedDraft(packMultipleInformation(manager));
            return;
        }

        Die chosenDie = computeRandomDieValue(draft.removeDieFromCopy(setUpInfoUnit.getSourceIndex()));
        setUpInfoUnit.setColor(chosenDie.getDieColor());
        setUpInfoUnit.setValue(chosenDie.getActualDieValue());
        manager.getControllerMaster().getGameState().getCurrentTurn().incrementDieCount();
        manager.showDraftedDie(manager.getControllerMaster().getGameState().getCurrentPlayer(), setUpInfoUnit);
    }
}
