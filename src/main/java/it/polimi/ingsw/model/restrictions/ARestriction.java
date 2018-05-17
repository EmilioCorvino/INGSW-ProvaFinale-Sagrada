package it.polimi.ingsw.model.restrictions;

import it.polimi.ingsw.model.die.Die;

/**
 * This class manages the restrictions associated to each cell of the windowPatternCard matrix.
 */
public abstract class ARestriction {


    /**
     * This method compares the attributes of a die with the restrictions contained in a specific cell.
     * @param die: the selected die.
     * @return true if the matching is correct.
     */
    public abstract boolean isRespected(Die die);
}
