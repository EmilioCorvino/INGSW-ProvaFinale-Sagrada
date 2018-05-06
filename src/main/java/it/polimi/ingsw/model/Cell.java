package it.polimi.ingsw.model;

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
    private List<ARestriction> ruleSetCell = new ArrayList<>();

    /**
     *
     * @param row The row of the cell
     * @param col The column of the cell
     * @param restriction The default restriction of a cell.
     */
    public Cell(int row, int col, ARestriction restriction) {
        this.row = row;
        this.col = col;
        if(restriction != null)
          this.ruleSetCell.add(restriction);
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

    /**
     * This method places a die in the cell, and update the role set with the color and the value of the die.
     * @param die is the die which need to be place in the cell
     */
    public void setContainedDie(Die die) {

        this.containedDie = die;
        ColorRestriction color = new ColorRestriction(die.getDieColor());
        ValueRestriction value = new ValueRestriction(die.getActualDieValue());
        ArrayList<ARestriction> dieRules = new ArrayList<>();
        dieRules.add(color);
        dieRules.add(value);

        updateRuleSet(dieRules);
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
     * @param rulesToAdd rules that need to be add to the ruleSet.
     */
    public void updateRuleSet(List<ARestriction> rulesToAdd) {
        this.ruleSetCell.clear();
        this.ruleSetCell.addAll(rulesToAdd);
    }

}
