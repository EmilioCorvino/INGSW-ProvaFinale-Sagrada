package it.polimi.ingsw.model.die.diecontainers;

import it.polimi.ingsw.model.AContainer;
import it.polimi.ingsw.model.die.Die;

/**
 * This class manages all types of die containers.
 */
public abstract class ADieContainer extends AContainer {

    /**
     * This method updates the die container involved with the die tha player wants to place.
     * @param die: the die that has to be placed.
     */
    public abstract void update(Die die);

    /**
     * This method computes if a given die is contained in a specific container.
     * @param die the die to search.
     * @return true if the given die is contained, false otherwise.
     */
    public abstract boolean isContained(Die die);

    /**
     * This method removes a die from a specific container.
     * @param die the die to remove.
     */
    public abstract void removeDie(Die die);

    //public abstract Die getSpecificDie(Die)
}
