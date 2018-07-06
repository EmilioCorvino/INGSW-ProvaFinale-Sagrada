package it.polimi.ingsw.server.model.cards.objective.publics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test of Row Color Variety card.
 */
public class RowColorVarietyTest extends CardTestField {

    /**
     * Tests if the card gives the correct score.
     */
    @Test
    public void cardTest() {
        setUp();
        this.card = deck.getDeck().get(0); //Row Color Variety card.
        this.card.accept(visitor, window);

        assertEquals(12, this.visitor.getScoreFromCard());
        tearDown();
    }
}
