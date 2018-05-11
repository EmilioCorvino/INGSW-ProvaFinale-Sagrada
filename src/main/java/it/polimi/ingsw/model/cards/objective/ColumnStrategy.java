package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.WindowPatternCard;

public class ColumnStrategy implements IScoreComputationStrategy {

    @Override
    public int applyColorStrategy(ColorPublicObjectiveCard card, WindowPatternCard window) {
        return 0;
    }

    @Override
    public int applyValueStrategy(ValuePublicObjectiveCard card, WindowPatternCard window) {
        return 0;
    }
}
