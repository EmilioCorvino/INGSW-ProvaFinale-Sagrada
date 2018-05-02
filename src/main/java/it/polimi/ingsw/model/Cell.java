package it.polimi.ingsw.model;

import java.util.*;

/**
 * This class manages the cells objects of which the window pattern card matrix is made.
 */
public class Cell {

    private int row;
    private int col;

    private Die containedDie;

    /**
     * The set of rules associated to a cell.
     */
    private Set<ARestriction> ruleSetCell;

    public Cell(int row, int col, Set<ARestriction> ruleSetCell) {
        // to complete
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

    public boolean isEmpty() {
        return (this.getContainedDie() == null);
    }

    public Set<ARestriction> getRuleSetCell() {
        return ruleSetCell;
    }

    public void setRuleSetCell(Set<ARestriction> ruleSetCell) {
        this.ruleSetCell = ruleSetCell;
    }
}
