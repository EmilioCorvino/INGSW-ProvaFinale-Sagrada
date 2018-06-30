package it.polimi.ingsw.model.die.containers;

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
    private String errorMessage;

    /**
     * This method updates the die container involved with the die tha player wants to place.
     * @param die: the die that has to be placed.
     */
    public abstract void addDieToCopy(Die die);

    /**
     * This method removes a die from a specific container.
     * @param index a copy of the die to be removed.
     * @return the die contained, that has been removed.
     */
    public abstract Die removeDieFromCopy(int index);

    /**
     * This method create a copy of the objects contained.
     */
    public abstract void createCopy();

    /**
     * This method move the copy of the objects contained to the original.
     */
    public abstract void overwriteOriginal();

    /**
     * This method computes if a given die is contained in a specific container.
     * @param die the die to search.
     * @return true if the given die is contained, false otherwise.
     */
    public abstract boolean isContained(Die die);

    public String getErrorMessage() {
        return errorMessage;
    }

    protected void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
