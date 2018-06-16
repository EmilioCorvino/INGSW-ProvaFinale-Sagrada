package it.polimi.ingsw.model.cards.tool.ValueEffects;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;

import java.util.List;
import java.util.Random;

/**
 * This class manages the effect of the tool card that allow the user to relaunch the chosen die.
 */
public class DraftValueEffect extends AValueEffect {

    /**
     * This attribute is the number of dice that has to be drafted. If is equal to 0 means that all dice of the draft pool must be rolled.
     */
    private int limit;

    /**
     * Constructs this effect with a default value for limit.
     */
    public DraftValueEffect() {
        this.limit = 0;
    }

    /**
     * Construct this effect with a specific value for limit.
     * @param limit the limit parameter to set.
     */
    public DraftValueEffect(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
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
     * @param manager the controller.
     * @param setUpInfoUnit the information to use.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        if(this.limit == 0) {
            List<Die> diceToDraft = manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice();
            diceToDraft.forEach(die -> die.setActualDieValue(computeRandomDieValue(die).getActualDieValue()));
            //TODO tell the manager to show updates.
            return;
        }

        Die chosenDie = computeRandomDieValue(manager.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice().get(setUpInfoUnit.getSourceIndex()));
        WindowPatternCard wp = manager.getControllerMaster().getGameState().getCurrentPlayer().getWindowPatternCard();

        if(!super.checkExistingCellsToUse(wp, chosenDie)) {
            manager.showNotification("Non ci sono celle disponibili in cui il dado può essere piazzato");
            return;
        }

        wp.setDesiredCell(new Cell(setUpInfoUnit.getDestinationIndex() / WindowPatternCard.MAX_COL, setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL));
        wp.addDie(chosenDie);
        //TODO tell the controller to show updates.
    }
}