package it.polimi.ingsw.model.cards.objective.publics.strategies;

import it.polimi.ingsw.model.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

/**
 * This interface is part of the strategy pattern used to decide in which way the window pattern card should be analyzed
 * (e.g. by rows, columns, sets, diagonals).
 */
public interface IScoreComputationStrategy {

    /**
     * Applies the strategy relative to {@link it.polimi.ingsw.model.Color} of the implementing classes to the window.
     * @param card from which taking the parameters.
     * @param window to which the strategy applies.
     */
    int applyColorStrategy(ColorPublicObjectiveCard card, WindowPatternCard window);

    /**
     * Applies the strategy relative to {@link it.polimi.ingsw.model.Shade} of the implementing classes to the window.
     * @param card from which taking the parameters.
     * @param window to which the strategy applies.
     */
    int applyValueStrategy(ValuePublicObjectiveCard card, WindowPatternCard window);
}
