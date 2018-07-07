package it.polimi.ingsw.server.model.die;

import it.polimi.ingsw.common.Color;

/**
 * This class manges the Die object.
 */
public class Die {

    /**
     * The numeric value of the die that wil be set at the moment of its construction. It can change over the game play.
     */
    private int actualDieValue;

    /**
     * The dieColor value of the die that wil be set at the moment of its construction.
     */
    private Color dieColor;

    /**
     * The attribute that will contain the original value of the die.
     */
    private final int originalDieValue;

    public Die(int dieValue, Color color) {
        this.actualDieValue = dieValue;
        this.dieColor = color;
        this.originalDieValue = dieValue;
    }

    public int getActualDieValue() {
        return actualDieValue;
    }

    public void setActualDieValue(int actualDieValue) {
        this.actualDieValue = actualDieValue;
    }

    public Color getDieColor() {
        return dieColor;
    }

    public int getOriginalDieValue() {
        return originalDieValue;
    }

    @Override
    public String toString() {
        String toBeReturned;
        toBeReturned = Integer.toString(getActualDieValue()) + " " + getDieColor().name();

        return toBeReturned;
    }
}
