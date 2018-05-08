package it.polimi.ingsw.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class WindowPatternTest {

    /**
     * This test verify if a placement of a die that respect the placement role, effective place the die in the glassWindow.
     */
    @Test
    public void placement(){
        WindowPatternCard wp = new WindowPatternCard(1,1,null);
        Die die = new Die(1, Color.RED);
        Cell selectedCell = new Cell(0,0,null);

        if(wp.canBePlaced(die,selectedCell))
            wp.update(die);

        assertEquals(wp.getGlassWindow()[selectedCell.getRow()][selectedCell.getCol()].getContainedDie(),die);
    }

    /**
     * This method test if at the first placement the die can be place just in a border cell.
     */
    @Test
    public void firstPlacement () {
        WindowPatternCard WP = new WindowPatternCard(1,1,null);
        Die die = new Die(1, Color.RED);

        Cell selectedCell1 = new Cell(0,0,null);
        assertTrue(WP.canBePlaced(die,selectedCell1));

        Cell selectedCell2 = new Cell(3,4,null);
        assertTrue(WP.canBePlaced(die,selectedCell2));

        Cell selectedCell3 = new Cell(1,1,null);
        assertFalse(WP.canBePlaced(die,selectedCell3));
    }

    /**
     * This method test if a die can't be placed in a not empty cell.
     */
    @Test
    public void placementBusyCell(){
        WindowPatternCard WP = new WindowPatternCard(1,1,null);
        Cell selectedCell = new Cell (0,1,null);
        Die die1 = new Die(2, Color.RED);
        Die die2 = new Die(3, Color.BLUE);

        WP.canBePlaced(die1, selectedCell);
        WP.update(die1);
        assertFalse(WP.canBePlaced(die2,selectedCell));
    }

    /**
     * This test verify if a die placement, after an other one, is done in a cell adjacent to the previous one.
     */
    @Test
    public void AdjacentRole(){
        WindowPatternCard wp = new WindowPatternCard(1,1,null);
        Cell selectedCell1 = new Cell (0,1,null);
        Cell selectedCell2 = new Cell (0,2,null); //Border cell adjacent
        Cell selectedCell3 = new Cell (1,2,null); //Cell diagonal adjacent
        Cell selectedCell4 = new Cell (2, 1, null); //Cell not adjacent
        Die die1 = new Die(2, Color.RED);
        Die die2 = new Die(3,Color.BLUE);

        wp.canBePlaced(die1,selectedCell1);
        wp.update(die1);
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
        WindowPatternCard WP = new WindowPatternCard(1,1,null);
        Cell cell1 = new Cell(0,0,null);
        Cell orizAdjacentCell = new Cell(0, 1,null);
        Cell diagAdjacentCell = new Cell(1, 1, null);
        Die die = new Die(1,Color.RED);
        Die correctDie = new Die(2,Color.GREEN);
        Die incorrectValueDie  = new Die(1,Color.BLUE);
        Die incorrectColorDie = new Die(3, Color.RED);

        if(WP.canBePlaced(die,cell1))
            WP.update(die);
        assertTrue(WP.canBePlaced(correctDie, orizAdjacentCell));
        assertFalse(WP.canBePlaced(incorrectColorDie, orizAdjacentCell));
        assertFalse(WP.canBePlaced(incorrectValueDie,orizAdjacentCell));

         //This assertion verify that is possible to place a die with the same color or value, in a position diagonal adjacent.

        assertTrue(WP.canBePlaced(incorrectColorDie,diagAdjacentCell));
    }
}