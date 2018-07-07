package it.polimi.ingsw.server.model.cards.objective.publics.strategies;

import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.server.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.server.model.cards.objective.publics.ValuePublicObjectiveCard;
import it.polimi.ingsw.server.model.die.Cell;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCard;

import java.util.HashSet;
import java.util.Set;

/**
 * Strategy for row based score computation.
 */
public class RowStrategy implements IScoreComputationStrategy {

    /**
     * This method computes the score from the card by checking how many rows have dice with different colors
     * and giving a fixed amount of points for each of them.
     * @param card from which taking the parameters.
     * @param window to which the strategy is applied.
     * @return points given by the card.
     */
    @Override
    public int applyColorStrategy(ColorPublicObjectiveCard card, WindowPatternCard window) {
        int cardPoints = 0;
        for(Cell[] row: window.getGlassWindow()) {
            if(hasDistinctColors(row)) {
                cardPoints += card.getPointsForCompletion();
            }
        }
        return cardPoints;
    }

    /**
     * This method computes the score from the card by checking how many rows have dice with different values
     * and giving a fixed amount of points for each of them.
     * @param card from which taking the parameters.
     * @param window to which the strategy is applied.
     * @return points given by the card.
     */
    @Override
    public int applyValueStrategy(ValuePublicObjectiveCard card, WindowPatternCard window) {
        int cardPoints = 0;
        for(Cell[] row: window.getGlassWindow()) {
            if(hasDistinctValues(row)) {
                cardPoints += card.getPointsForCompletion();
            }
        }
        return cardPoints;
    }

    /**
     * Checks if the row consists in dice of different colors and if each cell of the row contains a die.
     * @param cells a row of the {@link WindowPatternCard}
     * @return {@code true} if the row has dice with distinct colors, {@code false} otherwise.
     */
    private boolean hasDistinctColors(Cell[] cells) {
        Set<Color> foundColors = new HashSet<>();
        return (StrategyUtils.areFullAndHaveDifferentColors(cells, foundColors));
    }

    /**
     * Checks if the row consists in dice of different values and if each cell of the row contains a die.
     * @param cells a row of the {@link WindowPatternCard}
     * @return {@code true} if the row has dice with distinct values, {@code false} otherwise.
     */
    private boolean hasDistinctValues(Cell[] cells) {
        Set<Integer> foundValues = new HashSet<>();
        return (StrategyUtils.areFullAndHaveDifferentValues(cells, foundValues));
    }


}