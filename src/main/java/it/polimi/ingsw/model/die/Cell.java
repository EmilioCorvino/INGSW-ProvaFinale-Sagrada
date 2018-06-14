package it.polimi.ingsw.model.die;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;

import java.util.ArrayList;
import java.util.List;

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
    private transient List<ARestriction> ruleSetCell;

    /**
     * The value restriction of the cell on the Window pattern card.
     */
    private ColorRestriction defaultColorRestriction;

    /**
     * The color restriction of the cell on the window pattern card
     */
    private ValueRestriction defaultValueRestriction;

    /**
     *
     * @param row The row of the cell
     * @param col The column of the cell
     * @param restriction The restriction of a cell on the window pattern card.
     */
    public Cell(int row, int col, ColorRestriction restriction) {
        this.row = row;
        this.col = col;
        this.ruleSetCell = new ArrayList<>();
        this.ruleSetCell.add(restriction);
        this.defaultColorRestriction = restriction;
        this.defaultValueRestriction = new ValueRestriction(0);
        this.containedDie = new Die(0, Color.BLANK);
    }

    public Cell(int row, int col, ValueRestriction restriction){
        this.row = row;
        this.col = col;
        this.ruleSetCell = new ArrayList<>();
        this.ruleSetCell.add(restriction);
        this.defaultColorRestriction = new ColorRestriction(Color.BLANK);
        this.defaultValueRestriction = restriction;
        this.containedDie = new Die(0, Color.BLANK);
    }

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.ruleSetCell = new ArrayList<>();
        this.defaultColorRestriction = new ColorRestriction(Color.BLANK);
        this.defaultValueRestriction = new ValueRestriction(0);
        this.containedDie = new Die(0, Color.BLANK);
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

    public void setDefaultColorRestriction(ColorRestriction defaultColorRestriction) {
        this.defaultColorRestriction = defaultColorRestriction;
    }

    public void setDefaultValueRestriction(ValueRestriction defaultValueRestriction) {
        this.defaultValueRestriction = defaultValueRestriction;
    }

    public ColorRestriction getDefaultColorRestriction() {
        return defaultColorRestriction;
    }

    public ValueRestriction getDefaultValueRestriction() {
        return defaultValueRestriction;
    }

    /**
     * This method places a die in the cell, and addDie the role set with the color and the value of the die.
     * @param die is the die which need to be place in the cell
     */
    public void setContainedDie(Die die) {
        this.containedDie = die;
        if( die.getActualDieValue() != 0 && !die.getDieColor().equals(Color.BLANK)) {
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
        setContainedDie(new Die(0, Color.BLANK));
        return die;
    }

    public List<ARestriction> getRuleSetCell() {
        return ruleSetCell;
    }

    public void setRuleSetCell(List<ARestriction> ruleSetCell) {
        this.ruleSetCell = ruleSetCell;
    }

    /**
     * This method check if the cell is Empty.
     * @return true if the cell doesn't contain a die.
     */
    public boolean isEmpty() {
        return (this.getContainedDie() == null || (this.getContainedDie().getActualDieValue() == 0 && this.getContainedDie().getDieColor().equals(Color.BLANK)));
    }

    /**
     * This method addDie the cell's ruleSet.
     * @param rulesToAdd rules that need to be addDie to the ruleSet, null when the player want to remove a die.
     */
    public void updateRuleSet(List<ARestriction> rulesToAdd) {
        this.ruleSetCell.clear();
        if(rulesToAdd != null)
            this.ruleSetCell.addAll(rulesToAdd);
        else{
            if (defaultValueRestriction.getValue() != 0)
                this.ruleSetCell.add(defaultValueRestriction);
            else if(!defaultColorRestriction.getColor().equals(Color.BLANK))
                this.ruleSetCell.add(defaultColorRestriction);
        }
    }



}
