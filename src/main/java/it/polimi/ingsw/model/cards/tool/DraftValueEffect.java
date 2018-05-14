package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.ADieContainer;
import it.polimi.ingsw.model.Die;

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
    

    @Override
    public void executeMove(Die chosenDie, ADieContainer destinationContainer) {

    }
}