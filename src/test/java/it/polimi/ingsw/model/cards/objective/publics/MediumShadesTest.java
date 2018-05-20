package it.polimi.ingsw.model.cards.objective.publics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test of Medium Shades card.
 */
public class MediumShadesTest extends CardTestField {

    /**
     * Tests if the card gives the correct score.
     */
    @Test
    public void cardTest() {
        setUp();
        this.card = deck.getDeck().get(7); //Medium Shades card.
        this.card.accept(visitor, window);

        assertEquals(4, this.visitor.getScoreFromCard());
        tearDown();
    }
}
