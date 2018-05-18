package it.polimi.ingsw.model.cards.objective.publics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test of Color Variety card.
 */
public class ColorVarietyTest extends Card {

    /**
     * Tests if the card gives the correct score.
     */
    @Test
    public void cardTest() {
        setUp();
        this.card = deck.getDeck().get(3); //Column Color Variety card.
        this.card.accept(visitor, window);

        assertEquals(8, this.visitor.getScoreFromCard());
        tearDown();
    }
}
