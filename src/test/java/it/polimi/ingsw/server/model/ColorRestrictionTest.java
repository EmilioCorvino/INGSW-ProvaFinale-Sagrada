package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.restrictions.ColorRestriction;
import it.polimi.ingsw.server.model.restrictions.IRestriction;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ColorRestrictionTest {

    @Test
    public void isRespected(){

        Die dieTest = new Die(5, Color.BLUE);
        IRestriction colorRestriction1 = new ColorRestriction(Color.BLUE);
        assertTrue(colorRestriction1.isRespected(dieTest));

        IRestriction colorRestriction2 = new ColorRestriction(Color.RED);
        assertFalse(colorRestriction2.isRespected(dieTest));
    }
}
