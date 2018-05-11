package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.WindowPatternCard;

import java.util.HashSet;
import java.util.Set;

public class RowStrategy implements IScoreComputationStrategy {

    private String strategyName;

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    @Override
    public int applyColorStrategy(ColorPublicObjectiveCard card, WindowPatternCard window) {
        int cardPoints = 0;
        for(Cell[] row: window.getGlassWindow()) {
            if(hasDistinctColors(row)) {
                cardPoints += card.getPointsForIteration();
            }
        }
        return cardPoints;
    }

    @Override
    public int applyValueStrategy(ValuePublicObjectiveCard card, WindowPatternCard window) {
        return 0;
    }

    private boolean hasDistinctColors(Cell[] cells) {
        Set<Color> foundColors = new HashSet<>();
        for(Cell c: cells){
            if(foundColors.contains(c.getContainedDie().getDieColor())){
                return false;
            }
            foundColors.add(c.getContainedDie().getDieColor());
        }
        return true;
    }
}
