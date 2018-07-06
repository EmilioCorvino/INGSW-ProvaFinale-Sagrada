package it.polimi.ingsw.server.model.restrictions;

import it.polimi.ingsw.server.model.die.Die;

/**
 * This class manages the value restriction of a cell.
 */
public class ValueRestriction extends ARestriction {

    /**
     * the value restriction associated to a cell.
     */
    private int value;

    public ValueRestriction(int value) {
        this.value = value;
    }

    /**
     * This method manages the value restriction of a cell.
     * @param die the selected die.
     * @return {@code true} if the value of the die is equal to the value restriction of the cell, {@code false} otherwise.
     */
    public boolean isRespected(Die die) {
        return die.getActualDieValue() == this.getValue();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
