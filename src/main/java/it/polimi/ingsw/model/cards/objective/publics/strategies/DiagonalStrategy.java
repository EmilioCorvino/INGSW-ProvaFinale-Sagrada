package it.polimi.ingsw.model.cards.objective.publics.strategies;

import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

public class DiagonalStrategy implements IScoreComputationStrategy {

    @Override
    public int applyColorStrategy(ColorPublicObjectiveCard card, WindowPatternCard window) {
        return 0;
    }

    @Override
    public int applyValueStrategy(ValuePublicObjectiveCard card, WindowPatternCard window) {
        return 0;
    }
}
