package it.polimi.ingsw.model.move;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.ADieContainer;

/**
 *
 */
public class DiePlacementMove implements IMove {

    /**
     *
     * @param chosenDie
     * @param destinationContainer
     */
    @Override
    public void executeMove(Die chosenDie, ADieContainer destinationContainer) {
        destinationContainer.update(chosenDie);
    }
}
