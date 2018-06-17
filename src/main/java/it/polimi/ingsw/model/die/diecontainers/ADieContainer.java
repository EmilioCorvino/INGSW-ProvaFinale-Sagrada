package it.polimi.ingsw.model.die.diecontainers;

import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;

/**
 * This class manages all types of die containers.
 */
public abstract class ADieContainer {

    /**
     * This is the error message set after any failure.
     * @see WindowPatternCard#canBePlaced(Die, Cell, Cell[][])
     */
    protected String errorMessage;

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

    public String getErrorMessage() {
        return errorMessage;
    }

    protected void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
