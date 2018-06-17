package it.polimi.ingsw.model;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DiceDraftPoolTest {

    /**
     * This test verify the correct population of the DraftPool
     */
    @Test
    public void populateDiceDraftPool() {
        DiceDraftPool diceDraftPool = new DiceDraftPool();
        int numberOfPlayer = 4;

        diceDraftPool.populateDiceDraftPool(numberOfPlayer);

        assertEquals(numberOfPlayer*2 +1, diceDraftPool.getAvailableDice().size());
    }

    /**
     * This test verify if an addDie of a Draft pool effective remove a die from the Draft.
     */
    @Test
    public void checkUpdate() {
        DiceDraftPool diceDraftPool = new DiceDraftPool();
        int numberOfPlayer = 4;

        diceDraftPool.populateDiceDraftPool(numberOfPlayer);
        diceDraftPool.createCopy();

        int index = new Random().nextInt(numberOfPlayer*2 + 1);

        diceDraftPool.removeDie(index);
        diceDraftPool.overwriteOriginal();

        assertEquals( numberOfPlayer*2 + 1 - 1 , diceDraftPool.getAvailableDice().size());

    }

    @Test
    public void checkAdd(){
        DiceDraftPool diceDraftPool = new DiceDraftPool();
        int numberOfPlayer = 4;
        Die die = new Die(2, Color.RED);

        diceDraftPool.populateDiceDraftPool(numberOfPlayer);
        diceDraftPool.createCopy();

        diceDraftPool.addDie(die);
        diceDraftPool.overwriteOriginal();

        assertEquals(numberOfPlayer*2 + 1 +1, diceDraftPool.getAvailableDice().size());
        assertEquals(die.getDieColor(), diceDraftPool.getAvailableDice().get(diceDraftPool.getAvailableDice().size()-1).getDieColor());
        assertEquals(die.getActualDieValue(), diceDraftPool.getAvailableDice().get(diceDraftPool.getAvailableDice().size()-1).getActualDieValue());
    }
}