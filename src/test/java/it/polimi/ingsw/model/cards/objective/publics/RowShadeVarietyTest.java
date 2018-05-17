package it.polimi.ingsw.model.cards.objective.publics;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test of Row Shade Variety card.
 */
public class RowShadeVarietyTest extends Card {
    /**
     * Tests if the card gives the correct score.
     */
    @Test
    public void cardTest() {
        setUp();
        this.card = deck.getDeck().get(4); //Row Shade Variety card.
        this.card.accept(visitor, window);

        assertEquals(5, this.visitor.getScoreFromCard());
        tearDown();
    }
}
