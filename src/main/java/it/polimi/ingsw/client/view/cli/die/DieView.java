package it.polimi.ingsw.client.view.cli.die;

import it.polimi.ingsw.common.Color;

/**
 * This class identify a die in the view context.
 */
public class DieView {

    /**
     * Color of the die.
     */
    private Color dieColor;

    /**
     * Value of the die.
     */
    private int dieValue;

    public DieView(Color dieColor, int dieValue){
        this.dieColor = dieColor;
        this.dieValue = dieValue;
    }

    public Color getDieColor() {
        return dieColor;
    }

    /**
     * Converts the die into a string.
     * @return a string representing the die.
     */
    public String toStringDie(){
        return "\033["+this.dieColor.getColorNumber()+";1m"+dieValue+"\033[0m";
    }
}
