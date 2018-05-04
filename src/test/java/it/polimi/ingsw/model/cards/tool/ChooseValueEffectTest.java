package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Die;
import org.junit.Test;

import static org.junit.Assert.*;

public class IncreaseAValueEffectTest {

    @Test
    public void increaseDieValue() {

        Die die = new Die(4, Color.BLUE);
        AValueEffect toolEffect = new ChooseValueEffect();
        assertEquals(5, ((ChooseValueEffect) toolEffect).increaseDieValue(die).getActualDieValue() );

        Die die1 = new Die(4, Color.PURPLE);
        AValueEffect toolEffect1 = new ChooseValueEffect(1);
        assertEquals(5, ((ChooseValueEffect) toolEffect1).increaseDieValue(die1).getActualDieValue());


        Die die2 = new Die(4, Color.YELLOW);
        AValueEffect toolEffect2 = new ChooseValueEffect(1);

        for(int i=0; i<2; i++)
            ((ChooseValueEffect) toolEffect2).increaseDieValue(die2);

        assertEquals(5, die2.getActualDieValue());

        Die die3 = new Die(6, Color.GREEN);
        AValueEffect toolEffect3 = new ChooseValueEffect(1);
        assertEquals(6, ((ChooseValueEffect) toolEffect3).increaseDieValue(die3).getActualDieValue());
    }
}