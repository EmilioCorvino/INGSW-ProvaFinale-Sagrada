package it.polimi.ingsw.model;

import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WindowPatternTest {

    /**
     * This test verify if a placement of a die that respect the placement role, effective place the die in the glassWindow.
     */
    @Test
    public void placement(){
        WindowPatternCard wp = new WindowPatternCard(1,1);
        Die die = new Die(1, Color.RED);
        Cell selectedCell = new Cell(0,0);

        wp.createCopy();

        if(wp.canBePlaced(die,selectedCell, wp.getGlassWindow())) {
            wp.setDesiredCell(selectedCell);
            wp.addDie(die);
        }

        wp.overwriteOriginal();
        assertEquals(wp.getGlassWindow()[selectedCell.getRow()][selectedCell.getCol()].getContainedDie().getActualDieValue(),die.getActualDieValue());
        assertEquals(wp.getGlassWindow()[selectedCell.getRow()][selectedCell.getCol()].getContainedDie().getDieColor(),die.getDieColor());
    }

    /**
     * This method test if at the first placement the die can be place just in a border cell.
     */
    @Test
    public void firstPlacement () {
        WindowPatternCard wp= new WindowPatternCard(1,1);
        Die die = new Die(1, Color.RED);

        Cell selectedCell1 = new Cell(0,0);
        assertTrue(wp.canBePlaced(die,selectedCell1, wp.getGlassWindow()));

        Cell selectedCell2 = new Cell(3,4);
        assertTrue(wp.canBePlaced(die,selectedCell2, wp.getGlassWindow()));

        Cell selectedCell3 = new Cell(1,1);
        assertFalse(wp.canBePlaced(die,selectedCell3, wp.getGlassWindow()));
    }

    /**
     * This method test if a die can't be placed in a not empty cell.
     */
    @Test
    public void placementBusyCell(){
        WindowPatternCard wp = new WindowPatternCard(1,1);
        Cell selectedCell = new Cell (0,1);
        Die die1 = new Die(2, Color.RED);
        Die die2 = new Die(3, Color.BLUE);

        wp.createCopy();

        if(wp.canBePlaced(die1, selectedCell, wp.getGlassWindow())) {
            wp.setDesiredCell(selectedCell);
            wp.addDie(die1);
        }

        wp.overwriteOriginal();
        assertFalse(wp.canBePlaced(die2,selectedCell, wp.getGlassWindow()));
    }

    /**
     * This test verify if a die placement, after an other one, is done in a cell adjacent to the previous one.
     */
    @Test
    public void AdjacentRole(){
        WindowPatternCard wp = new WindowPatternCard(1,1);
        Cell selectedCell1 = new Cell (0,1);
        Cell selectedCell2 = new Cell (0,2); //Border cell adjacent
        Cell selectedCell3 = new Cell (1,2); //Cell diagonal adjacent
        Cell selectedCell4 = new Cell (2, 1); //Cell not adjacent
        Die die1 = new Die(2, Color.RED);
        Die die2 = new Die(3,Color.BLUE);

        wp.createCopy();

        if(wp.canBePlaced(die1,selectedCell1, wp.getGlassWindow())) {
            wp.setDesiredCell(selectedCell1);
            wp.addDie(die1);
        }

        wp.overwriteOriginal();
        assertTrue(wp.canBePlaced(die2,selectedCell2, wp.getGlassWindow()));
        assertTrue(wp.canBePlaced(die2,selectedCell3, wp.getGlassWindow()));
        assertFalse(wp.canBePlaced(die2,selectedCell4, wp.getGlassWindow()));
    }

    /**
     * This method test the placement of a die in a cell with restriction
     */
    @Test
    public void OwnRestriction(){
        ColorRestriction c = new ColorRestriction(Color.BLUE);
        ValueRestriction v = new ValueRestriction(5);
        Cell cell1 = new Cell(0,0,c);
        Cell cell2 = new Cell(0,2,v);
        List<Cell> cells = new ArrayList<>();
        cells.add(cell1);
        cells.add(cell2);
        WindowPatternCard wp = new WindowPatternCard(1,1,cells);

        Die die1 = new Die(1,Color.BLUE);
        Die die2 = new Die(5, Color.RED);

        assertTrue(wp.canBePlaced(die1,cell1, wp.getGlassWindow()));
        assertFalse(wp.canBePlaced(die2,cell1, wp.getGlassWindow()));

        assertTrue(wp.canBePlaced(die2,cell2, wp.getGlassWindow()));
        assertFalse(wp.canBePlaced(die1,cell2, wp.getGlassWindow()));

    }

    /**
     * This test check if all the placement roles are respected.
     */
    @Test
    public void placementRestriction(){
        WindowPatternCard wp = new WindowPatternCard(1,1);
        Cell cell1 = new Cell(0,0);
        Cell orizAdjacentCell = new Cell(0, 1);
        Cell diagAdjacentCell = new Cell(1, 1);
        Die die = new Die(1,Color.RED);
        Die correctDie = new Die(2,Color.GREEN);
        Die incorrectValueDie  = new Die(1,Color.BLUE);
        Die incorrectColorDie = new Die(3, Color.RED);

        wp.createCopy();

        if(wp.canBePlaced(die,cell1, wp.getGlassWindow())){
            wp.setDesiredCell(cell1);
            wp.addDie(die);
        }

        wp.overwriteOriginal();
        assertTrue(wp.canBePlaced(correctDie, orizAdjacentCell, wp.getGlassWindow()));
        assertFalse(wp.canBePlaced(incorrectColorDie, orizAdjacentCell, wp.getGlassWindow()));
        assertFalse(wp.canBePlaced(incorrectValueDie,orizAdjacentCell, wp.getGlassWindow()));

         //This assertion verify that is possible to place a die with the same color or value, in a position diagonal adjacent.

        assertTrue(wp.canBePlaced(incorrectColorDie,diagAdjacentCell, wp.getGlassWindow()));
    }

    @Test
    public void testPlacementInsideWpParsed(){
        WindowPatternCard wp;
        Cell cell1 = new Cell(0,3);
        Cell cell2 = new Cell(0,1);
        Cell cell3 = new Cell(0,0);
        Cell cell4 = new Cell(1,3);
        Cell cell5 = new Cell(0,0);
        Cell cell6 = new Cell(0,2);

        Die die1 = new Die(4, Color.PURPLE);
        Die die2 = new Die(1, Color.RED);
        Die die3 = new Die(5, Color.BLUE);

        CommonBoard commonBoard = new CommonBoard();
        commonBoard.initializeBoard();

        wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(7);
        wp.createCopy();


        // Test of color restricted cell.
        assertTrue(wp.canBePlaced(die1,cell1, wp.getGlassWindow()));
        assertFalse(wp.canBePlaced(die2,cell1, wp.getGlassWindow()));

        // Test of value restricted cell.
        assertTrue(wp.canBePlaced(die2, cell2, wp.getGlassWindow()));
        assertFalse(wp.canBePlaced(die1, cell2, wp.getGlassWindow()));

        // Test of no restricted cell.
        assertTrue(wp.canBePlaced(die1, cell3, wp.getGlassWindow()));
        assertTrue(wp.canBePlaced(die2, cell3, wp.getGlassWindow()));

        //Test of no border cell
        assertFalse(wp.canBePlaced(die1, cell4, wp.getGlassWindow()));

        //Test of consecutive placement
        if(wp.canBePlaced(die2,cell2, wp.getGlassWindowCopy())) {
            wp.setDesiredCell(cell2);
            wp.addDie(die2);
        }
        wp.overwriteOriginal();
        assertFalse(wp.canBePlaced(die3, cell6, wp.getGlassWindowCopy()));
        assertEquals(wp.getGlassWindow()[cell2.getRow()][cell2.getCol()].getContainedDie().getDieColor(), die2.getDieColor());
        assertEquals(wp.getGlassWindow()[cell2.getRow()][cell2.getCol()].getContainedDie().getActualDieValue(), die2.getActualDieValue());
        assertTrue(wp.canBePlaced(die3, cell5, wp.getGlassWindow()));

    }

    @Test
    public void removeDie(){
        CommonBoard commonBoard = new CommonBoard();
        commonBoard.initializeBoard();
        WindowPatternCard wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(7);
        wp.createCopy();

        Die die = new Die(4, Color.PURPLE);
        Cell cell = new Cell(0,3);



        if (wp.canBePlaced(die,cell, wp.getGlassWindow())) {
            wp.setDesiredCell(cell);
            wp.addDie(die);
        }

        wp.overwriteOriginal();
        assertEquals(die.getActualDieValue(), wp.getGlassWindow()[cell.getRow()][cell.getCol()].getContainedDie().getActualDieValue());
        assertEquals(die.getDieColor(), wp.getGlassWindow()[cell.getRow()][cell.getCol()].getContainedDie().getDieColor());

        wp.setDesiredCell(cell);
        wp.removeDie(cell.getRow()*WindowPatternCard.MAX_COL+cell.getCol());

        wp.overwriteOriginal();
        assertNull(wp.getGlassWindow()[cell.getRow()][cell.getCol()].getContainedDie());

    }

}