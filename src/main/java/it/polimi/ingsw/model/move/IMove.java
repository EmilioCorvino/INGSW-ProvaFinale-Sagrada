package it.polimi.ingsw.model.move;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.ADieContainer;

/**
 *
 */
public interface IMove {

    /**
     *
     * @return
     */
    public void executeMove(Die chosenDie, ADieContainer destinationContainer);
}
