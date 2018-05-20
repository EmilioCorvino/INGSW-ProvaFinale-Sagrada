package it.polimi.ingsw.model.cards.objective.publics;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test of Coloured Diagonals card.
 */
public class ColouredDiagonalsTest extends CardTestField {

    /**
     * Tests if the card gives the correct score.
     */
    @Test
    public void cardTest() {
        //First test, done with the window pattern card inherited from CardTestField.
        setUp();
        this.card = deck.getDeck().get(2); //Coloured Diagonals card.
        this.card.accept(visitor, window);

        assertEquals(6, this.visitor.getScoreFromCard());

        //Second test, done with the window pattern card build in anotherTry() method.
        this.card.accept(visitor, anotherTry());

        assertEquals(7, this.visitor.getScoreFromCard());
        tearDown();
    }

    /**
     * Builds a supplementary {@link WindowPatternCard} to perform one more test.
     * @return a filled {@link WindowPatternCard}.
     */
    private WindowPatternCard anotherTry() {
        WindowPatternCard anotherWindow = new WindowPatternCard(0, 0, null);

        Die redDie1 = new Die(1, Color.RED);
        Die redDie2 = new Die(1, Color.RED);
        Die redDie3 = new Die(1, Color.RED);
        Die redDie4 = new Die(1, Color.RED);
        Die redDie5 = new Die(1, Color.RED);
        Die redDie6 = new Die(1, Color.RED);
        Die redDie7 = new Die(1, Color.RED);

        if(anotherWindow.canBePlaced(redDie1, anotherWindow.getGlassWindow()[0][2])) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[0][2]);
            anotherWindow.update(redDie1);
        }
        if(anotherWindow.canBePlaced(redDie2, anotherWindow.getGlassWindow()[1][3])) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[1][3]);
            anotherWindow.update(redDie2);
        }
        if(anotherWindow.canBePlaced(redDie3, anotherWindow.getGlassWindow()[2][2])) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[2][2]);
            anotherWindow.update(redDie3);
        }
        if(anotherWindow.canBePlaced(redDie4, anotherWindow.getGlassWindow()[2][4])) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[2][4]);
            anotherWindow.update(redDie4);
        }
        if(anotherWindow.canBePlaced(redDie5, anotherWindow.getGlassWindow()[3][3])) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[3][3]);
            anotherWindow.update(redDie5);
        }
        if(anotherWindow.canBePlaced(redDie6, anotherWindow.getGlassWindow()[3][1])) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[3][1]);
            anotherWindow.update(redDie6);
        }
        if(anotherWindow.canBePlaced(redDie7, anotherWindow.getGlassWindow()[2][0])) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[2][0]);
            anotherWindow.update(redDie7);
        }
        return anotherWindow;
    }
}
