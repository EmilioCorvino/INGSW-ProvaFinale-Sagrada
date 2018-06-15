package it.polimi.ingsw.model;

import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;
import it.polimi.ingsw.utils.exceptions.DieNotContainedException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class WindowPatternTest {

    /**
     * This test verify if a placement of a die that respect the placement role, effective place the die in the glassWindow.
     */
    @Test
    public void placement(){
        WindowPatternCard wp = new WindowPatternCard(1,1);
        Die die = new Die(1, Color.RED);
        Cell selectedCell = new Cell(0,0);

        if(wp.canBePlaced(die,selectedCell)) {
            wp.setDesiredCell(selectedCell);
            wp.addDie(die);
        }

        assertEquals(wp.getGlassWindow()[selectedCell.getRow()][selectedCell.getCol()].getContainedDie(),die);
    }

    /**
     * This method test if at the first placement the die can be place just in a border cell.
     */
    @Test
    public void firstPlacement () {
        WindowPatternCard wp= new WindowPatternCard(1,1);
        Die die = new Die(1, Color.RED);

        Cell selectedCell1 = new Cell(0,0);
        assertTrue(wp.canBePlaced(die,selectedCell1));

        Cell selectedCell2 = new Cell(3,4);
        assertTrue(wp.canBePlaced(die,selectedCell2));

        Cell selectedCell3 = new Cell(1,1);
        assertFalse(wp.canBePlaced(die,selectedCell3));
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

        if(wp.canBePlaced(die1, selectedCell)) {
            wp.setDesiredCell(selectedCell);
            wp.addDie(die1);
        }
        assertFalse(wp.canBePlaced(die2,selectedCell));
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

        if(wp.canBePlaced(die1,selectedCell1)) {
            wp.setDesiredCell(selectedCell1);
            wp.addDie(die1);
        }
        assertTrue(wp.canBePlaced(die2,selectedCell2));
        assertTrue(wp.canBePlaced(die2,selectedCell3));
        assertFalse(wp.canBePlaced(die2,selectedCell4));
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

        assertTrue(wp.canBePlaced(die1,cell1));
        assertFalse(wp.canBePlaced(die2,cell1));

        assertTrue(wp.canBePlaced(die2,cell2));
        assertFalse(wp.canBePlaced(die1,cell2));

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

        if(wp.canBePlaced(die,cell1)){
            wp.setDesiredCell(cell1);
            wp.addDie(die);
        }

        assertTrue(wp.canBePlaced(correctDie, orizAdjacentCell));
        assertFalse(wp.canBePlaced(incorrectColorDie, orizAdjacentCell));
        assertFalse(wp.canBePlaced(incorrectValueDie,orizAdjacentCell));

         //This assertion verify that is possible to place a die with the same color or value, in a position diagonal adjacent.

        assertTrue(wp.canBePlaced(incorrectColorDie,diagAdjacentCell));
    }

    @Test
    public void testPlacementInsideWpParsed(){
        WindowPatternCard wp;
        Cell cell1 = new Cell(0,3);
        Cell cell2 = new Cell(0,1);
        Cell cell3 = new Cell(0,0);
        Cell cell4 = new Cell(1,3);
        Cell cell5 = new Cell(0,0);

        Die die1 = new Die(4, Color.PURPLE);
        Die die2 = new Die(1, Color.GREEN);
        Die die3 = new Die(5, Color.BLUE);

        CommonBoard commonBoard = new CommonBoard();
        commonBoard.initializeBoard();

        wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(7);

        // Test of color restricted cell.
        assertTrue(wp.canBePlaced(die1,cell1));
        assertFalse(wp.canBePlaced(die2,cell1));

        // Test of value restricted cell.
        assertTrue(wp.canBePlaced(die2, cell2));
        assertFalse(wp.canBePlaced(die1, cell2));

        // Test of no restricted cell.
        assertTrue(wp.canBePlaced(die1, cell3));
        assertTrue(wp.canBePlaced(die2, cell3));

        //Test of no border cell
        assertFalse(wp.canBePlaced(die1, cell4));

        //Test of consecutive placement
        wp.setDesiredCell(cell2);
        wp.addDie(die2);
        assertEquals(wp.getGlassWindow()[cell2.getRow()][cell2.getCol()].getContainedDie(), die2);
        assertTrue(wp.canBePlaced(die3, cell5));

    }

    @Test
    public void removeDie(){
        CommonBoard commonBoard = new CommonBoard();
        WindowPatternCard wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(7);

        Die die = new Die(4, Color.PURPLE);
        Cell cell = new Cell(0,3);



        if (wp.canBePlaced(die,cell)) {
            wp.setDesiredCell(cell);
            wp.addDie(die);
        }

        assertEquals(die, wp.getGlassWindow()[cell.getRow()][cell.getCol()].getContainedDie());

        wp.setDesiredCell(cell);
        wp.removeDie(cell.getRow()*WindowPatternCard.MAX_COL+cell.getCol());


        assertNull(wp.getGlassWindow()[cell.getRow()][cell.getCol()].getContainedDie());

    }

}