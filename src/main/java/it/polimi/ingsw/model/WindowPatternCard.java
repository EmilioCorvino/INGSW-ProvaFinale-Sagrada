package it.polimi.ingsw.model;

/**
 * This class manages the window pattern card associated to each player.
 */
public class WindowPatternCard extends ADieContainer {

    private int idMap;

    /**
     * The difficulty of the window pattern card. It is equal to the number of favor tokens to assign to each player.
     */
    private int difficulty;

    /**
     * The matrix that will effectively contain the dice the player will choose.
     */
    private Cell[][] glassWindow;

    public WindowPatternCard() {
        //to complete
    }

    /**
     * This method sets the window pattern card with the selected die.
     * @param die: the die with which the window pattern card has to be updated.
     */
    public void update(Die die) {
        //to complete
    }

    /**
     * Checks if the the selected cell on the border of the matrix. This method is used at the first turn of the first round for each player.
     * @param selectedCell: the cell where the player wants to put the die.
     * @return true if the selected cell is one of the cells of the border.
     */
    public boolean checkBorderCells(Cell selectedCell) {
        //to complete
        return true;
    }

    /**
     * Checks if the selected cell is adjiacent to other non empty cells.
     * @param selectedCell: the cell where the player wants to put the die.
     * @return true if the selected cell is adjacent to other non empty cells.
     */
    public boolean checkAdjacentCells(Cell selectedCell) {
        //to complete
        return true;
    }

    /**
     * Checks if the selected die can be placed in a selected cell of the window glass.
     * @param die: the die the player wants to place.
     * @param selectedCell: the cell where the player wants to put the die.
     * @return true if the die can be placed.
     */
    public boolean canBePlaced(Die die, Cell selectedCell) {
        //to complete
        return true;
    }

    public int getIdMap() {
        return idMap;
    }

    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Cell[][] getGlassWindow() {
        return glassWindow;
    }

    public void setGlassWindow(Cell[][] glassWindow) {
        this.glassWindow = glassWindow;
    }
}
