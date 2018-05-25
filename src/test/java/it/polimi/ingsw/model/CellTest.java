package it.polimi.ingsw.model;

import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class CellTest {

    /**
     * This test check if at the creation of the cell, it is empty, and when a die will be place it will be not.
     */
    @Test
    public void emptyCheck() {

        Cell cell = new Cell(1, 1);
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

        Cell cell = new Cell(1,1);

        Die die = new Die(5, Color.RED);
        cell.setContainedDie(die);

        assertEquals(cell.getContainedDie(), die);
    }

    /**
     * This test check if a die placement effective modify the cell's role set.
     */
    @Test
    public void updateSetRoleCheck (){

        Cell cell = new Cell(1,1);
        Die die = new Die(2, Color.RED);

        cell.setContainedDie(die);

        List<ARestriction> rules = cell.getRuleSetCell();

        for (int i = 0; i < rules.size(); i++)
            assertTrue(rules.get(i).isRespected(die));

    }

    /**
     * This Test verify if the default restriction is added in the set of rules of the cell.
     */
    @Test
    public void checkCellDefaultRestriction(){
        ColorRestriction c = new ColorRestriction(Color.BLUE);
        Cell cell = new Cell(0,0,c);

        assertFalse(cell.getRuleSetCell().size() == 0);
        assertEquals(cell.getRuleSetCell().get(0), c);
    }

    /**
     * This test verify if a role set is clear when a die is remove
     */
    @Test
    public void checkRemove(){
        Cell cell = new Cell(0,0);
        Die die = new Die(1,Color.RED);
        ColorRestriction c = new ColorRestriction(Color.RED);
        Cell cellRestricted = new Cell (1,1, c);

        cell.setContainedDie(die);

        assertEquals(die, cell.removeContainedDie());

        cellRestricted.setContainedDie(die);

        assertEquals(die, cellRestricted.removeContainedDie());
        assertEquals(cellRestricted.getRuleSetCell().get(0),c);
    }
}
