package it.polimi.ingsw.model.die;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;

import java.util.*;

/**
 * This class manages the cells objects of which the window pattern card matrix is made.
 */
public class Cell {

    private int row;
    private int col;

    /**
     * Die contained in the cell
     */
    private Die containedDie;

    /**
     * The set of rules associated to a cell.
     */
    private List<ARestriction> ruleSetCell;

    /**
     * The restriction of the cell on the Window pattern card.
     */
    private ARestriction defaultRestriction;

    /**
     *
     * @param row The row of the cell
     * @param col The column of the cell
     * @param restriction The restriction of a cell on the window pattern card.
     */
    public Cell(int row, int col, ARestriction restriction) {
        this.row = row;
        this.col = col;
        this.ruleSetCell = new ArrayList<>();
        this.ruleSetCell.add(restriction);
        this.defaultRestriction = restriction;
        this.containedDie = null;
    }

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.ruleSetCell = new ArrayList<>();
        this.defaultRestriction = null;
        this.containedDie = null;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Die getContainedDie() {
        return containedDie;
    }

    public ARestriction getDefaultRestriction() {
        return defaultRestriction;
    }

    public void setDefaultRestriction(ARestriction defaultRestriction) {
        this.defaultRestriction = defaultRestriction;
    }

    /**
     * This method places a die in the cell, and update the role set with the color and the value of the die.
     * @param die is the die which need to be place in the cell
     */
    public void setContainedDie(Die die) {
        this.containedDie = die;
        if( die != null) {
            ColorRestriction color = new ColorRestriction(die.getDieColor());
            ValueRestriction value = new ValueRestriction(die.getActualDieValue());
            ArrayList<ARestriction> dieRules = new ArrayList<>();
            dieRules.add(color);
            dieRules.add(value);
            updateRuleSet(dieRules);
        }
        else{
            updateRuleSet(null);
        }

    }

    /**
     * This class remove the die that is contained, can be use when a die is move to an other cell or an other die container.
     * @return The die extracted.
     */
    public Die removeContainedDie(){

        Die die = getContainedDie();
        setContainedDie(null);
        return die;
    }

    public List<ARestriction> getRuleSetCell() {
        return ruleSetCell;
    }

    /**
     * This method check if the cell is Empty.
     * @return true if the cell doesn't contain a die.
     */
    public boolean isEmpty() {
        return (this.getContainedDie() == null);
    }

    /**
     * This method update the cell's ruleSet.
     * @param rulesToAdd rules that need to be add to the ruleSet, null when the player want to remove a die.
     */
    public void updateRuleSet(List<ARestriction> rulesToAdd) {
        this.ruleSetCell.clear();
        if(rulesToAdd != null)
            this.ruleSetCell.addAll(rulesToAdd);
        else if (defaultRestriction != null)
            this.ruleSetCell.add(defaultRestriction);
    }

}
