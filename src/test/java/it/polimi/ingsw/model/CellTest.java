package it.polimi.ingsw.model;

import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CellTest {

    /**
     * This test check if at the creation of the cell, it is empty, and when a die will be place it will be not.
     */
    @Test
    public void emptyCheck() {

        Cell cell = new Cell(1, 1 , new ArrayList<ARestriction>());
        assertTrue(cell.isEmpty());

        Die die = new Die(5, Color.RED);
        cell.setContainedDie(die);
        assertFalse(cell.isEmpty());
    }

    /**
     * This test check if a die placement effective modify the contained die in the cell.
     */
    @Test
    public void placementDieCheck() {

        Cell cell = new Cell(1,1, new ArrayList<ARestriction>());

        Die die = new Die(5, Color.RED);
        cell.setContainedDie(die);

        assertEquals(cell.getContainedDie(), die);
    }

    /**
     * This test check if a die placement effective modify the cell's role set.
     */
    @Test
    public void updateSetRoleCheck (){

        Cell cell = new Cell(1,1 , new ArrayList<ARestriction>());
        Die die = new Die(2, Color.RED);

        cell.setContainedDie(die);

        List<ARestriction> rules = cell.getRuleSetCell();

        for (int i = 0; i < rules.size(); i++)
            assertTrue(rules.get(i).isRespected(die));

    }
}
