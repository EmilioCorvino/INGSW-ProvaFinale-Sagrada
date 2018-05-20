package it.polimi.ingsw.model.cards.objective.publics;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test of Column Color Variety card.
 */
public class ColumnColorVarietyTest extends CardTestField {

    /**
     * Tests if the card gives the correct score.
     */
    @Test
    public void cardTest() {
        setUp();
        this.card = deck.getDeck().get(1); //Column Color Variety card.
        this.card.accept(visitor, window);

        assertEquals(5, this.visitor.getScoreFromCard());
        tearDown();
    }
}
