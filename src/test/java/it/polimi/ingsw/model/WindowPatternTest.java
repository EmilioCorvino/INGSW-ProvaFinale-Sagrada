package it.polimi.ingsw.model;

import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCardDeck;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;
import it.polimi.ingsw.utils.exceptions.EmptyException;
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
            wp.update(die);
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
            wp.update(die1);
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
            wp.update(die1);
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
            wp.update(die);
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
        Cell cell1 = new Cell(0,4);
        Cell cell2 = new Cell(0,2);
        Cell cell3 = new Cell(0,1);
        Cell cell4 = new Cell(1,3);

        Die die1 = new Die(4, Color.GREEN);
        Die die2 = new Die(2, Color.YELLOW);

        List<WindowPatternCard> card = new ArrayList<>();
        CommonBoard commonBoard = new CommonBoard();

        try {
            card = commonBoard.getWindowPatternCardDeck().drawCard();
        } catch (EmptyException e){
            SagradaLogger.log(Level.SEVERE, "Error: try to draw window pattern card in an empty deck");
        }
        wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(0);

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


    }

}