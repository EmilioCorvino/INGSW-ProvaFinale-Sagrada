package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

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
     * This test verify if an update of a Draft pool effective remove a die from the Draft.
     */
    @Test
    public void checkUpdate() {
        DiceDraftPool diceDraftPool = new DiceDraftPool();
        int numberOfPlayer = 4;

        diceDraftPool.populateDiceDraftPool(numberOfPlayer);
        Die die = diceDraftPool.getAvailableDice().get(new Random().nextInt(numberOfPlayer*2 + 1));

        diceDraftPool.update(die);
        assertEquals( numberOfPlayer*2 + 1 - 1 , diceDraftPool.getAvailableDice().size());

    }
}