package it.polimi.ingsw.client.view.cli.die;

import it.polimi.ingsw.common.Color;

/**
 * This class identify a cell
 */
public class CellView {

    /**
     * Reference to the {@link DieView} contained in the cell.
     */
    private DieView die;

    /**
     * Color restriction of the cell.
     */
    private Color defaultColorRestriction;

    /**
     * Value restriction of the cell.
     */
    private int defaultValueRestriction;

    CellView(Color defaultColorRestriction, int defaultValueRestriction){
        this.defaultColorRestriction = defaultColorRestriction;
        this.defaultValueRestriction = defaultValueRestriction;
    }

    /**
     * Allows to convert the cell in a string.
     * @return a string representing the cell.
     */
    String toStringCell(){
        if(die != null)
            return die.toStringDie();
        else if (defaultColorRestriction != Color.BLANK)
            return "\033["+defaultColorRestriction.getColorNumber()+"m"+defaultColorRestriction.getId()+"\033[0m";
        else if(defaultValueRestriction != 0)
            return ""+defaultValueRestriction;
        else
            return" ";
    }

    public void removeDie(){
        die = null;
    }

    public void setDie(DieView die) {
        this.die = die;
    }
}
