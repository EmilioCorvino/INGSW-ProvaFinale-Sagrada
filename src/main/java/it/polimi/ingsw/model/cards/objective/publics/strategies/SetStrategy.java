package it.polimi.ingsw.model.cards.objective.publics.strategies;

import it.polimi.ingsw.utils.exceptions.AbsentAttributeException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class SetStrategy implements IScoreComputationStrategy {

    /**
     * This method computes the score from the card by checking how many complete sets of dice with different colors
     * there are in the {@link WindowPatternCard}. This number is the same as the minimum number of dice with a certain
     * color.
     * For each completed set, this method gives a certain number of points, defined in the card.
     * @param card from which taking the parameters.
     * @param window to which the strategy is applied.
     * @return points given by the card.
     */
    @Override
    public int applyColorStrategy(ColorPublicObjectiveCard card, WindowPatternCard window) {
        EnumMap<Color, Integer> diceAccumulators = new EnumMap<>(Color.class);
        //Initialization of accumulators in the EnumMap.
        for(Color c: Color.values()) {
            diceAccumulators.put(c, 0);
        }
        //Iteration of the window pattern card to count different dices on a color basis.
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for(int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if(!window.getGlassWindow()[i][j].isEmpty()) {
                    Color dieColor = window.getGlassWindow()[i][j].getContainedDie().getDieColor();
                    Integer oldAccumulator = diceAccumulators.get(dieColor);
                    diceAccumulators.replace(dieColor, oldAccumulator + 1);
                }
            }
        }
        //Filling of an array to compute the minimum accumulator.
        Integer[] arrayAccumulators = new Integer[Color.values().length];
        int index = 0;
        for(Color c: Color.values()) {
            arrayAccumulators[index] = diceAccumulators.get(c);
            index++;
        }
        return card.getPointsForCompletion() * findMinimumValue(arrayAccumulators);
    }

    /**
     * This method computes the score from the card by checking how many sets of dice with different values
     * (aka {@link it.polimi.ingsw.model.Shade}) there are in the {@link WindowPatternCard}. This number is the same as
     * the minimum number of dice with a certain value.
     * For each completed set, this method gives a certain number of points, defined in the card.
     * There are different set based cards, so this methods returns the right value based on
     * {@link it.polimi.ingsw.model.Shade}
     * @param card from which taking the parameters.
     * @param window to which the strategy is applied.
     * @return points given by the card.
     * @throws AbsentAttributeException if the {@link it.polimi.ingsw.model.Shade} is not set in the card.
     */
    @Override
    public int applyValueStrategy(ValuePublicObjectiveCard card, WindowPatternCard window) {
        switch(card.getShade()) {
            case LIGHT: return lightShadesSets(window) * card.getPointsForCompletion();
            case MEDIUM: return mediumShadesSets(window) * card.getPointsForCompletion();
            case DARK: return darkShadesSets(window) * card.getPointsForCompletion();
            case ALL: return allShadesSets(window) * card.getPointsForCompletion();
        }
        throw new AbsentAttributeException("Impossible to find card shade");
    }

    /**
     * Counts the complete sets of dice with light shades (1 and 2).
     * @param window to analyze.
     * @return the number of complete sets composed by 1 and 2 (which is the minimum number of dice with
     * either value 1 or 2).
     */
    private int lightShadesSets(WindowPatternCard window) {
        int lighterShade = 0;
        int darkerShade = 0;
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for(int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if(!window.getGlassWindow()[i][j].isEmpty()) {
                    int dieValue = window.getGlassWindow()[i][j].getContainedDie().getActualDieValue();
                    if(dieValue == 1) {
                        lighterShade++;
                    } else if(dieValue == 2) {
                        darkerShade++;
                    }
                }
            }
        }
        return (lighterShade < darkerShade) ? lighterShade : darkerShade;
    }

    /**
     * Counts the complete sets of dice with medium shades (3 and 4).
     * @param window to analyze.
     * @return the number of complete sets composed by 3 and 4 (which is the minimum number of dice with
     * either value 3 or 4).
     */
    private int mediumShadesSets(WindowPatternCard window) {
        int lighterShade = 0;
        int darkerShade = 0;
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for(int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if(!window.getGlassWindow()[i][j].isEmpty()) {
                    int dieValue = window.getGlassWindow()[i][j].getContainedDie().getActualDieValue();
                    if(dieValue == 3) {
                        lighterShade++;
                    } else if(dieValue == 4) {
                        darkerShade++;
                    }
                }
            }
        }
        return (lighterShade < darkerShade) ? lighterShade : darkerShade;
    }

    /**
     * Counts the complete sets of dice with light shades (5 and 6).
     * @param window to analyze.
     * @return the number of complete sets composed by 5 and 6 (which is the minimum number of dice with
     * either value 5 or 6).
     */
    private int darkShadesSets(WindowPatternCard window) {
        int lighterShade = 0;
        int darkerShade = 0;
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for(int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if(!window.getGlassWindow()[i][j].isEmpty()) {
                    int dieValue = window.getGlassWindow()[i][j].getContainedDie().getActualDieValue();
                    if(dieValue == 5) {
                        lighterShade++;
                    } else if(dieValue == 6) {
                        darkerShade++;
                    }
                }
            }
        }
        return (lighterShade < darkerShade) ? lighterShade : darkerShade;
    }

    /**
     * Counts the complete sets of dice with all shades.
     * @param window to analyze.
     * @return the number of complete sets composed each possible value (from 1 to 6).
     */
    private int allShadesSets(WindowPatternCard window) {
        Map<Integer, Integer> diceAccumulators = new HashMap<>();
        //Initialization of accumulators in the Map.
        for(int value = 1; value <= 6; value++) {
            diceAccumulators.put(value, 0);
        }
        //Iteration of the window pattern card to count different dices on a value basis.
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for(int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                if(!window.getGlassWindow()[i][j].isEmpty()) {
                    int dieValue = window.getGlassWindow()[i][j].getContainedDie().getActualDieValue();
                    Integer oldAccumulator = diceAccumulators.get(dieValue);
                    diceAccumulators.replace(dieValue, oldAccumulator + 1);
                }
            }
        }
        //Filling of an array to compute the minimum accumulator.
        Integer[] arrayAccumulators = new Integer[6]; //The array is as big as the number of possible dice values.
        int index = 0;
        for(int value = 1; value <= 6; value++) {
            arrayAccumulators[index] = diceAccumulators.get(value);
            index++;
        }
        return findMinimumValue(arrayAccumulators);
    }

    /**
     * Finds the minimum value in an Integer array.
     * @param diceValues array containing the value of the dices of which minimum is to be computed.
     * @return the minimum value of the array.
     */
    private int findMinimumValue(Integer[] diceValues) {
        Integer min = diceValues[0];
        for(Integer i: diceValues) {
            if(i < min) {
                min = i;
            }
        }
        return min;
    }
}
