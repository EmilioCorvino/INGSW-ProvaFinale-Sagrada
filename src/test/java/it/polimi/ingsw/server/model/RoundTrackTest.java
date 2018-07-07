package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.die.containers.RoundTrack;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RoundTrackTest {

    /**
     * This test verify round track updates (both addDieToCopy and remove dice).
     */
    @Test
    public void multiplePopulation(){
        int nRounds = 10;
        int roundWithRemove = new Random().nextInt(10);
        RoundTrack roundTrack = new RoundTrack(nRounds);
        roundTrack.createCopy();

        for ( int i = 0; i < nRounds; i++) {
            roundTrack.setRoundToBeUpdated(i);
            for (int j = 0; j < 2; j++)
                roundTrack.addDieToCopy(new Die(1 + new Random().nextInt(6), Color.availableColors().get(new Random().nextInt(5))));
        }
        roundTrack.overwriteOriginal();

        for (int k = 0; k < nRounds; k++)
            assertEquals(2, roundTrack.getAvailableDice().get(k).size());


        roundTrack.setRoundToBeUpdated(roundWithRemove);
        roundTrack.removeDieFromCopy(0);
        roundTrack.overwriteOriginal();

        assertEquals(1, roundTrack.getAvailableDice().get(roundWithRemove).size());
    }
}