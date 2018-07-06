package it.polimi.ingsw.server.model.restrictions;

import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.server.model.die.Die;

/**
 * This class manages the color restriction of a cell.
 */
public class ColorRestriction extends ARestriction {

    /**
     * The color restriction associated to a cell.
     */
    private Color color;

    public ColorRestriction (Color color){
        this.color = color;
    }

    /**
     * This method manages the color restriction of a cell.
     * @param die: the selected die.
     * @return true if the color of the die equals the color restriction of the cell.
     */
    public boolean isRespected(Die die) {
        return die.getDieColor() == this.getColor();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}