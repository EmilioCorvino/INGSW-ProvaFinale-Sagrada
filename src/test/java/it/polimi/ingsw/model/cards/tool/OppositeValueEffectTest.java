package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.tool.ValueEffects.AValueEffect;
import it.polimi.ingsw.model.cards.tool.ValueEffects.OppositeValueEffect;
import it.polimi.ingsw.model.die.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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