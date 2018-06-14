package it.polimi.ingsw.model.die.diecontainers;

import it.polimi.ingsw.model.AContainer;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.utils.exceptions.DieNotContainedException;

/**
 * This class manages all types of die containers.
 */
public abstract class ADieContainer extends AContainer {

    /**
     * This method updates the die container involved with the die tha player wants to place.
     * @param die: the die that has to be placed.
     */
    public abstract void addDie(Die die);

    /**
     * This method computes if a given die is contained in a specific container.
     * @param die the die to search.
     * @return true if the given die is contained, false otherwise.
     */
    public abstract boolean isContained(Die die);

    /**
     * This method removes a die from a specific container.
     * @param index a copy of the die to be removed.
     * @return the die contained, that has been removed.
     */
    public abstract Die removeDie(int index);

    //public abstract Die getSpecificDie(Die)
}
