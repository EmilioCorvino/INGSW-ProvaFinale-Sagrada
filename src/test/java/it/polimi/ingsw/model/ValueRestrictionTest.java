package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValueRestrictionTest {

    @Test
    public void isRespected() {

        Die dieTest = new Die(5, Color.RED);
        ARestriction valueRestriction = new ValueRestriction(5);
        assertTrue(valueRestriction.isRespected(dieTest));

        ARestriction valueRestriction1 = new ValueRestriction(3);
        assertFalse(valueRestriction1.isRespected(dieTest));
    }
}