package it.polimi.ingsw.server.model.cards.objective.publics.strategies;

import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.server.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.server.model.cards.objective.publics.ValuePublicObjectiveCard;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCard;

/**
 * This interface is part of the strategy pattern used to decide in which way the window pattern card should be analyzed
 * (e.g. by rows, columns, sets, diagonals).
 */
public interface IScoreComputationStrategy {

    /**
     * Applies the strategy relative to {@link Color} of the implementing classes to the window.
     * @param card from which taking the parameters.
     * @param window to which the strategy is applied.
     * @return points given by the card.
     */
    int applyColorStrategy(ColorPublicObjectiveCard card, WindowPatternCard window);

    /**
     * Applies the strategy relative to {@link it.polimi.ingsw.server.model.Shade} of the implementing classes to the window.
     * @param card from which taking the parameters.
     * @param window to which the strategy is applied.
     * @return points given by the card.
     */
    int applyValueStrategy(ValuePublicObjectiveCard card, WindowPatternCard window);
}
