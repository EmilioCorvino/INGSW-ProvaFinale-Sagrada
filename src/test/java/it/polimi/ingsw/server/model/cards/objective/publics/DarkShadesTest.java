package it.polimi.ingsw.server.model.cards.objective.publics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test of Dark Shades card.
 */
public class DarkShadesTest extends CardTestField {

    /**
     * Tests if the card gives the correct score.
     */
    @Test
    public void cardTest() {
        setUp();
        this.card = deck.getDeck().get(8); //Dark Shades card.
        this.card.accept(visitor, window);

        assertEquals(6, this.visitor.getScoreFromCard());
        tearDown();
    }
}