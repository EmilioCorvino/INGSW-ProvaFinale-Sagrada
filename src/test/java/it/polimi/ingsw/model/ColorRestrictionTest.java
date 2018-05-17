package it.polimi.ingsw.model;

import it.polimi.ingsw.model.die.Color;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ColorRestriction;
import org.junit.Test;
import static org.junit.Assert.*;

public class ColorRestrictionTest {

    @Test
    public void isRespected(){

        Die dieTest = new Die(5, Color.BLUE);
        ARestriction colorRestriction1 = new ColorRestriction(Color.BLUE);
        assertTrue(colorRestriction1.isRespected(dieTest));

        ARestriction colorRestriction2 = new ColorRestriction(Color.RED);
        assertFalse(colorRestriction2.isRespected(dieTest));
    }
}
