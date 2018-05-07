package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.WindowPatternCard;

/**
 * This interface is part of the strategy pattern used to decide in which way the window pattern card should be analyzed
 * (e.g. by rows, columns, sets, diagonals).
 */
public interface IScoreComputationStrategy {

    /**
     * Applies the strategy of the implementing classes to the window.
     * @param window to which the strategy applies.
     */
    public void applyStrategy(WindowPatternCard window);
}
