package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.Color;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.restrictions.IRestriction;
import it.polimi.ingsw.server.model.restrictions.ValueRestriction;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValueRestrictionTest {

    @Test
    public void isRespected() {

        Die dieTest = new Die(5, Color.RED);
        IRestriction valueRestriction = new ValueRestriction(5);
        assertTrue(valueRestriction.isRespected(dieTest));

        IRestriction valueRestriction1 = new ValueRestriction(3);
        assertFalse(valueRestriction1.isRespected(dieTest));
    }
}