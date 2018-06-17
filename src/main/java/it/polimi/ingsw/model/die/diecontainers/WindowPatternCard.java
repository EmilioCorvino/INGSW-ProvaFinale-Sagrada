package it.polimi.ingsw.model.die.diecontainers;

import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.restrictions.ARestriction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the window pattern card associated to each player.
 */
public class WindowPatternCard extends ADieContainer {

    /**
     * Maximum values of row and column.
     */
    public static final int MAX_COL = 5;
    public static final int MAX_ROW = 4;

    /**
     * The code that identify the map.
     */
    private int idMap;

    /**
     * The difficulty of the window pattern card. It is equal to the number of favor tokens to assign to each player.
     */
    private int difficulty;

    /**
     * The matrix that will effectively contain the dice the player will choose.
     */
    private Cell[][] glassWindow = new Cell[MAX_ROW][MAX_COL];

    /**
     * The cell where the player wants to put the die.
     */
    private Cell desiredCell;

    /**
     * This attributes indicates if the original glass window has been modified
     */
    private boolean isGlassWindowModified = false;

    /**
     * The copy of the glass window.
     */
    private Cell[][] glassWindowCopy = new Cell[MAX_ROW][MAX_COL];


    /**
     *This constructor is used for create window pattern card with restriction.
     * @param idMap: The code that identify a map.
     * @param difficulty: Difficulty of the window pattern card. It is equal to the number of favor tokens to assign to each player.
     * @param restrictedCells: List of Cells with color or value restriction.
     */
    public WindowPatternCard(int idMap, int difficulty, List<Cell> restrictedCells) {
            this.idMap = idMap;
            this.difficulty = difficulty;
            for (int i = 0; i < MAX_ROW; i++)
                for(int j = 0; j < MAX_COL; j++)
                    glassWindow[i][j] = new Cell(i,j);
            for (Cell c : restrictedCells)
                glassWindow [c.getRow()] [c.getCol()] = c;
    }

    /**
     * This constructor is used for create window Pattern Card without restriction.
     * @param idMap: the code that identify a map.
     * @param difficulty:  Difficulty of the window pattern card. It is equal to the number of favor tokens to assign to each player.
     */
    public WindowPatternCard(int idMap, int difficulty){
        this.idMap = idMap;
        this.difficulty = difficulty;
        for (int i = 0; i < MAX_ROW; i++)
            for(int j = 0; j < MAX_COL; j++)
                glassWindow[i][j] = new Cell(i,j);
    }


    /**
     * Constructor that generates a copy of the current class window.
     */
    public void createCopy() {
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for (int j = 0; i < WindowPatternCard.getMaxCol(); j++) {
                glassWindowCopy[i][j] = new Cell(i, j);
                if (!glassWindow[i][j].isEmpty())
                    glassWindowCopy[i][j].setContainedDie(new Die(glassWindow[i][j].getContainedDie().getActualDieValue(), glassWindow[i][j].getContainedDie().getDieColor()));
                else
                    glassWindowCopy[i][j].setRuleSetCell(glassWindow[i][j].getRuleSetCell());
            }
        }
    }

    /**
     * This method restore the original glass window.
     */
    private void overwriteOriginal() {
        for(int i = 0; i < WindowPatternCard.getMaxRow(); i++) {
            for (int j = 0; i < WindowPatternCard.getMaxCol(); j++) {
                glassWindow[i][j] = new Cell(i, j);
                if (!glassWindowCopy[i][j].isEmpty())
                    glassWindow[i][j].setContainedDie(new Die(glassWindowCopy[i][j].getContainedDie().getActualDieValue(), glassWindowCopy[i][j].getContainedDie().getDieColor()));
                else
                    glassWindow[i][j].setRuleSetCell(glassWindowCopy[i][j].getRuleSetCell());
            }
        }
    }


    /**
     * This method sets the window pattern card with the selected die.
     * @param die: the die with which the window pattern card has to be updated.
     */
    @Override
    public void addDie(Die die) {
        if(this.isGlassWindowModified())
            overwriteOriginal();
        glassWindow[desiredCell.getRow()][desiredCell.getCol()].setContainedDie(die);
        setDesiredCell(null);
    }

    /**
     * This method remove the die in the desired cell only if the die contained is the same.
     * @param index a copy of the die to be removed.
     * @return the die contained, that has been removed.
     */
    @Override
    public Die removeDie(int index){
        Cell cell = glassWindow[ index/WindowPatternCard.MAX_COL ][ index%WindowPatternCard.MAX_COL ];
        return cell.removeContainedDie();
    }

    /**
     * This method check if all the cells of the matrix are empty.
     * @return True if is all the cells are empty, otherwise false.
     * @param glassWindowToConsider
     */
    private boolean matrixIsEmpty(Cell[][] glassWindowToConsider) {
        boolean matrixEmpty = true;

        for (int i = 0; i < MAX_ROW; i++)
            for (int j = 0; j < MAX_COL; j++)
                if (!(glassWindowToConsider[i][j].isEmpty()))
                    matrixEmpty = false;

        return matrixEmpty;
    }

    /**
     * Checks if the the selected cell on the border of the matrix. This method is used at the first turn of the first round for each player.
     * @param selectedCell: the cell where the player wants to put the die.
     * @return true if the selected cell is one of the cells of the border.
     */
    private boolean checkBorderCells(Cell selectedCell) {
        if(selectedCell.getCol() == 0 || selectedCell.getCol() == MAX_COL-1 || selectedCell.getRow() == 0 || selectedCell.getRow() == MAX_ROW-1)
            return true;
        setErrorMessage("La cella selezionata non e' una cella del bordo");
        return false;
    }

    /**
     * Checks if the selected cell is adjacent to other non empty cells.
     * @param selectedCell : the cell where the player wants to put the die.
     * @param glassWindowToConsider : glass window on which the check is done.
     * @return true if the selected cell is adjacent to other non empty cells.
     */
    public boolean checkAdjacentCells(Cell selectedCell, Cell[][] glassWindowToConsider) {
        int minR = 0;
        int maxR = 3;
        int minC = 0;
        int maxC = 3;

        if(selectedCell.getRow() == 0)
            minR = 1;
        if(selectedCell.getRow() == MAX_ROW-1)
            maxR = 2;
        if(selectedCell.getCol() == 0)
            minC = 1;
        if(selectedCell.getCol() == MAX_COL-1)
            maxC = 2;

        for (int i = minR; i < maxR; i++)
            for (int j = minC; j < maxC; j++)
                if (!(i == 1 && j == i) && (!(glassWindowToConsider[selectedCell.getRow()-1 + i][selectedCell.getCol()-1 + j].isEmpty())))
                    return true;

        setErrorMessage("La cella desiderata non e' vicina ad un dado");
        return false;
    }

    /**
     * This method check if the die respects the restriction of the cell where the player wants to place it.
     * @param die : the die the player wants to place.
     * @param selectedCell : the cell where the player wants to place the die.
     * @param glassWindowToConsider : glass window on which the check is done.
     * @return true if the die respects all the restrictions of the selected cell.
     */
    public boolean checkOwnRuleSet(Die die, Cell selectedCell, Cell[][] glassWindowToConsider){
        boolean ok = true;

        for (ARestriction restriction : glassWindowToConsider[selectedCell.getRow()][selectedCell.getCol()].getRuleSetCell())
            if (!restriction.isRespected(die)) {
                ok = false;
                setErrorMessage("Non sono rispettate le restrizioni della cella desiderata.");
            }
        return ok;
    }

    /**
     * This method check if the die respects the restrictions of the adjacent cells.
     * @param die : Die that needs to be place.
     * @param selectedCell : the cell where the player wants to place the die.
     * @param glassWindowToConsider : glass window on which the check is done.
     * @return true if the die respects all the restrictions of the adjacent cells.
     */
    private boolean checkAdjacentRuleSet(Die die, Cell selectedCell, Cell[][] glassWindowToConsider) {

        List<ARestriction> adjacentRules = new ArrayList<>();
        if (selectedCell.getRow() != 0 && !glassWindowToConsider[selectedCell.getRow()-1][selectedCell.getCol()].isEmpty())
            adjacentRules.addAll( glassWindowToConsider[selectedCell.getRow()-1][selectedCell.getCol()].getRuleSetCell());

        if (selectedCell.getRow() != MAX_ROW-1 && !glassWindowToConsider[selectedCell.getRow()+1][selectedCell.getCol()].isEmpty())
            adjacentRules.addAll( glassWindowToConsider[selectedCell.getRow()+1][selectedCell.getCol()].getRuleSetCell());

        if(selectedCell.getCol() != 0 && !glassWindowToConsider[selectedCell.getRow()][selectedCell.getCol()-1].isEmpty())
            adjacentRules.addAll( glassWindowToConsider[selectedCell.getRow()][selectedCell.getCol()-1].getRuleSetCell());

        if(selectedCell.getCol() != MAX_COL-1 && !glassWindowToConsider[selectedCell.getRow()][selectedCell.getCol()+1].isEmpty())
            adjacentRules.addAll( glassWindowToConsider[selectedCell.getRow()][selectedCell.getCol()+1].getRuleSetCell());

        for (ARestriction restriction : adjacentRules)
            if(restriction.isRespected(die)) {
                setErrorMessage("Non sono rispettate le restrizioni delle celle adiacenti");
                return false;
            }

        return true;
    }

    private boolean isTheCellInTheMatrix(Cell cell){
        return (-1 < cell.getRow() && cell.getRow() < MAX_ROW) && (-1 < cell.getCol() && cell.getCol() < MAX_COL);
    }

    /**
     * Checks if the selected die can be placed in a selected cell of the window glass.
     * @param die : the die the player wants to place.
     * @param selectedCell : the cell where the player wants to put the die.
     * @param glassWindowToConsider glass window in which the method is applied.
     * @return true if the die can be placed.
     */
    public boolean canBePlaced(Die die, Cell selectedCell, Cell[][] glassWindowToConsider) {

        if (!isTheCellInTheMatrix(selectedCell) ){
            setErrorMessage("La cella desiderata non e' contenuta nella matrice");
            return false;
        }
        if (!glassWindowToConsider[selectedCell.getRow()][selectedCell.getCol()].isEmpty()){
            setErrorMessage("La cella desiderata e' gia piena");
            return false;
        }
        if (matrixIsEmpty(glassWindowToConsider)) {
            return checkBorderCells(selectedCell) && checkOwnRuleSet(die, selectedCell, glassWindowToConsider);
        }else {
            return checkAdjacentCells(selectedCell, glassWindowToConsider) &&
                    checkOwnRuleSet(die, selectedCell, glassWindowToConsider) &&
                    checkAdjacentRuleSet(die, selectedCell, glassWindowToConsider);
        }
    }

    /**
     * This method checks if a specific die is contained in the window pattern card
     * @param dieToSearch: die that should be contained.
     * @return true if is contained else false.
     */
    public boolean isContained(Die dieToSearch) {
        for(int i=0; i<MAX_ROW; i++)
            for(int j=0; i<MAX_COL; j++)
                if(!glassWindow[i][j].isEmpty())
                    return glassWindow[i][j].getContainedDie().equals(dieToSearch);
        return false;
    }


    public boolean isGlassWindowModified() {
        return isGlassWindowModified;
    }

    public void setGlassWindowModified(boolean glassWindowModified) {
        isGlassWindowModified = glassWindowModified;
    }

    public Cell[][] getGlassWindowCopy() {
        return glassWindowCopy;
    }

    public void setGlassWindowCopy(Cell[][] glassWindowCopy) {
        this.glassWindowCopy = glassWindowCopy;
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

    public Cell getDesiredCell() {
        return desiredCell;
    }

    public void setDesiredCell(Cell desiredCell) {
        this.desiredCell = desiredCell;
    }

    public static int getMaxCol() {
        return MAX_COL;
    }

    public static int getMaxRow() {
        return MAX_ROW;
    }
}
