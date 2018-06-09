package it.polimi.ingsw.model;

import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.RoundTrack;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class RoundTrackTest {

    /**
     * This test verify round track updates (both add and remove dice).
     */
    @Test
    public void multiplePopulation(){
        int nRounds = 10;
        int roundWithRemove = new Random().nextInt(10);
        RoundTrack roundTrack = new RoundTrack(nRounds);

        for ( int i = 0; i < nRounds; i++) {
            roundTrack.setRoundToBeUpdated(i);
            for (int j = 0; j < 2; j++)
                roundTrack.update(new Die(1 + new Random().nextInt(6), Color.availableColors().get(new Random().nextInt(5))));
        }

        for (int k = 0; k < nRounds; k++)
            assertEquals(2, roundTrack.getAvailableDice().get(k).size());


        roundTrack.setRoundToBeUpdated(roundWithRemove);
        roundTrack.remove(roundTrack.getAvailableDice().get(roundWithRemove).get(0));
        assertEquals(1, roundTrack.getAvailableDice().get(roundWithRemove).size());
    }
}