package it.polimi.ingsw.server.model.cards.objective.publics.strategies;

import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.server.model.die.Cell;

import java.util.Set;

/**
 * This class contains some utility methods used by other strategies. It should never be instantiated.
 * @see RowStrategy
 * @see ColumnStrategy
 */
class StrategyUtils {

    private StrategyUtils() {
        //Never instantiate this class!
        throw new IllegalStateException();
    }

    /**
     * Used to check if the {@code cells} are not empty and if they have already been added to the set of
     * {@code foundColors} (which, being a Set, cannot contain repeated elements).
     * @param cells array of {@link Cell} to check.
     * @param foundColors set containing colors already found.
     * @return {@code true} if the cell is not empty and isn't contained in the set, {@code false} otherwise.
     */
    static boolean areFullAndHaveDifferentColors(Cell[] cells, Set<Color> foundColors) {
        for(Cell c: cells){
            if(c.isEmpty()) {
                return false;
            }
            if(foundColors.contains(c.getContainedDie().getDieColor())){
                return false;
            }
            foundColors.add(c.getContainedDie().getDieColor());
        }
        return true;
    }

    /**
     * Used to check if the {@code cells} are not empty and if they have already been added to the set of
     * {@code foundValues} (which, being a Set, cannot contain repeated elements).
     * @param cells array of {@link Cell} to check.
     * @param foundValues set containing values already found.
     * @return {@code true} if the cell is not empty and isn't contained in the set, {@code false} otherwise.
     */
    static boolean areFullAndHaveDifferentValues(Cell[] cells, Set<Integer> foundValues) {
        for(Cell c: cells) {
            if(c.isEmpty()) {
                return false;
            }
            if(foundValues.contains(c.getContainedDie().getActualDieValue())) {
                return false;
            }
            foundValues.add(c.getContainedDie().getActualDieValue());
        }
        return true;
    }
}
