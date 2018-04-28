package it.polimi.ingsw.model;

/**
 * This class manges the Die object.
 */
public class Die {

    /**
     * The numeric value of the die that wil be set at the moment of its construction.
     */
    private int dieValue;

    /**
     * The color value of the die that wil be set at the moment of its construction.
     */
    //private Color dieColor;

    public Die(int dieValue) {
        this.dieValue = dieValue;
    }

    public int getDieValue() {
        return dieValue;
    }

    public void setDieValue(int dieValue) {
        this.dieValue = dieValue;
    }
}
