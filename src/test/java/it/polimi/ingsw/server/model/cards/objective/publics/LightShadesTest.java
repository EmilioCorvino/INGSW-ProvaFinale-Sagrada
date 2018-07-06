package it.polimi.ingsw.server.model.cards.objective.publics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test of Light Shades card.
 */
public class LightShadesTest extends CardTestField {

    /**
     * Tests if the card gives the correct score.
     */
    @Test
    public void cardTest() {
        setUp();
        this.card = deck.getDeck().get(6); //Light Shades card.
        this.card.accept(visitor, window);

        assertEquals(4, this.visitor.getScoreFromCard());
        tearDown();
    }
}
