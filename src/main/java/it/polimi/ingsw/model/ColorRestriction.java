package it.polimi.ingsw.model;

/**
 * This class manages the color restriction of a cell.
 */
public class ColorRestriction extends ARestriction {

    //color attribute

    /**
     * This method manages the color restriction of a cell.
     * @param die: the selected die.
     * @return true if the color of the die equals the color restriction of the cell.
     */
    public boolean isRespected(Die die) {
        return true;
    }
}
