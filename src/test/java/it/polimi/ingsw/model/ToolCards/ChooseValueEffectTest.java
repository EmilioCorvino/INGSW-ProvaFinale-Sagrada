package it.polimi.ingsw.model.ToolCards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Die;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChooseValueEffectTest {

    @Test
    public void increaseDieValue() {

        Die die = new Die(4, Color.BLUE);
        ValueEffect toolEffect = new ChooseValueEffect();
        assertEquals(5, ((ChooseValueEffect) toolEffect).increaseDieValue(die).getActualDieValue() );

        Die die1 = new Die(4, Color.PURPLE);
        ValueEffect toolEffect1 = new ChooseValueEffect(1);
        assertEquals(5, ((ChooseValueEffect) toolEffect1).increaseDieValue(die1).getActualDieValue());


        Die die2 = new Die(4, Color.YELLOW);
        ValueEffect toolEffect2 = new ChooseValueEffect(1);

        for(int i=0; i<2; i++)
            ((ChooseValueEffect) toolEffect2).increaseDieValue(die2);

        assertEquals(5, die2.getActualDieValue());

        Die die3 = new Die(6, Color.GREEN);
        ValueEffect toolEffect3 = new ChooseValueEffect(1);
        assertEquals(6, ((ChooseValueEffect) toolEffect3).increaseDieValue(die3).getActualDieValue());
    }

    @Test
    public void decreaseValue() {

        Die die = new Die(1, Color.BLUE);
        ValueEffect toolEffect = new ChooseValueEffect();
        assertEquals(1, ((ChooseValueEffect) toolEffect).decreaseValue(die).getActualDieValue());

        Die die1 = new Die(6, Color.PURPLE);
        ValueEffect toolEffect1 = new ChooseValueEffect(1);
        assertEquals(5, ((ChooseValueEffect) toolEffect1).decreaseValue(die1).getActualDieValue());
    }
}