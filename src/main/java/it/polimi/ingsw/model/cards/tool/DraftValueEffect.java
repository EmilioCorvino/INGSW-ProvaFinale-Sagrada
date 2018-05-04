package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Die;
import java.util.Random;

/**
 * This class manages the effect of the tool card that allow the user to relaunch the chosen die.
 */
public class DraftValueEffect extends AValueEffect {

    /**
     * This method computes a random value for the chosen die
     * @param chosenDie the die the player wants to
     * @return
     */
    private Die computeRandomDieValue(Die chosenDie) {
        Random randomNumber = new Random();
        chosenDie.setActualDieValue(randomNumber.nextInt(6) + 1);
        return chosenDie;
    }

    @Override
    public Die applyToolCardEffect(Die chosenDie) {
        return computeRandomDieValue(chosenDie);
    }
}
