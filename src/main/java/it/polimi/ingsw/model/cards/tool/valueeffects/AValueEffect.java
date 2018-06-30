
package it.polimi.ingsw.model.cards.tool.valueeffects;

import it.polimi.ingsw.model.cards.tool.AToolCardEffect;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the effects of the tool cards related to a change of the value of one or more dice.
 */
public abstract class AValueEffect extends AToolCardEffect {

    /**
     * The offset that determines the interval in which the value that will be chosen must respect.
     */
    protected int offset;

    /**
     * This attribute defines which method has to be called when the player wants to increase or decrease the value of a die.
     */
    //protected String symbol;

    /**
     *
     */
    protected Die chosenDie;

    public Die getChosenDie() {
        return chosenDie;
    }

    public void setChosenDie(Die chosenDie) {
        this.chosenDie = chosenDie;
    }

    /**
     * This constructor will set e default value for the offset in case it is not specified;
     */
    public AValueEffect() {
        this.offset = 0;
    }

    /**
     * This constructor will set a specific value to use when the effect will be applied.
     * @param offset: the specific value for the offset to set.
     */
    public AValueEffect(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    /*
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    */

    /**
     * This method checks if the result of an operation on the value of die is a right value.
     * @param dieValue: the value of the die.
     * @return true if the value that will be set is coherent.
     */
    protected boolean checkValue(int dieValue) {
        return dieValue >=1 && dieValue <= 6;
    }

    /**
     * This method checks if the value the user chooses is in the interval allowed by the effect of the tool card.
     * @param newValue: the value chosen by the player.
     * @param originalValue: the original value on which to execute the check.
     * @return true if the value is in the interval allowed by the effect of the tool card.
     */
    protected boolean checkNewValue(int newValue, int originalValue) {
        if(this.offset != 0)
            return newValue >= originalValue - this.offset && newValue <= originalValue + this.offset;
        return true;
    }

    protected boolean checkExistingCellsToUse(WindowPatternCard wp, Die chosenDie) {
        Cell[][] gw = wp.getGlassWindowCopy();
        List<Cell> cellToUse = new ArrayList<>();
        for(int i=0; i< WindowPatternCard.getMaxRow(); i++)
            for (int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                wp.setDesiredCell(gw[i][j]);
                if (wp.canBePlaced(chosenDie, wp.getDesiredCell(), wp.getGlassWindowCopy()))
                    cellToUse.add(gw[i][j]);
            }
        return cellToUse.size() > 0;
    }
}