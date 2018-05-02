package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.DieValueOutOfBoundsException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShadeTest {

    /**
     * Checks if the LIGHT shade gets correctly assigned. It also checks if the exception is thrown for
     * values lower than 1.
     */
    @Test
    public void lightShadeCheck() {
        for(int i = 0; i <= 2; i++) {
            Die die = new Die(i, Color.RED);
            try {
                assertEquals(Shade.LIGHT, Shade.getShadeFromValue(die));
            } catch (DieValueOutOfBoundsException e) {
                assertFalse(die.getActualDieValue() >= 1 && die.getActualDieValue() <= 6);
            }
        }
    }

    /**
     * Checks if the MEDIUM shade gets correctly assigned.
     */
    @Test
    public void mediumShadeCheck() {
        for(int i = 3; i <= 4; i++) {
            Die die = new Die(i, Color.RED);
            try {
                assertEquals(Shade.MEDIUM, Shade.getShadeFromValue(die));
            } catch (DieValueOutOfBoundsException e) {
                assertFalse(die.getActualDieValue() < 1 || die.getActualDieValue() > 6);
            }
        }
    }

    /**
     * Checks if the DARK shade gets correctly assigned. It also checks if the exception is thrown for
     * values higher than 6.
     */
    @Test
    public void darkShadeCheck() {
        for(int i = 5; i <= 7; i++) {
            Die die = new Die(i, Color.RED);
            try {
                assertEquals(Shade.DARK, Shade.getShadeFromValue(die));
            } catch (DieValueOutOfBoundsException e) {
                assertFalse(die.getActualDieValue() >= 1 && die.getActualDieValue() <= 6);
            }
        }
    }
}
