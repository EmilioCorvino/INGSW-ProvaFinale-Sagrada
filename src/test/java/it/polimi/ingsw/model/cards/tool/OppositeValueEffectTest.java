package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Die;
import org.junit.Test;

import static org.junit.Assert.*;

public class OppositeValueEffectTest {

    @Test
    public void computeOppositeValue() {
        Die die = new Die(5, Color.PURPLE);
        AValueEffect effect = new OppositeValueEffect(7);
        assertEquals(2, ((OppositeValueEffect) effect).computeOppositeValue(die).getActualDieValue());

        Die dieOut = new Die(10, Color.PURPLE);
        assertFalse(1 == ((OppositeValueEffect) effect).computeOppositeValue(dieOut).getActualDieValue());
    }

}