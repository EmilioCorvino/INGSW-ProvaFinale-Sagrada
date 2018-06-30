package it.polimi.ingsw.model.cards.objective.publics;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
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
        WindowPatternCard anotherWindow = new WindowPatternCard(0, 0);

        Die redDie1 = new Die(1, Color.RED);
        Die redDie2 = new Die(1, Color.RED);
        Die redDie3 = new Die(1, Color.RED);
        Die redDie4 = new Die(1, Color.RED);
        Die redDie5 = new Die(1, Color.RED);
        Die redDie6 = new Die(1, Color.RED);
        Die redDie7 = new Die(1, Color.RED);

        anotherWindow.createCopy();
        if(anotherWindow.canBePlaced(redDie1, anotherWindow.getGlassWindow()[0][2], anotherWindow.getGlassWindowCopy())) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[0][2]);
            anotherWindow.addDieToCopy(redDie1);
        }
        if(anotherWindow.canBePlaced(redDie2, anotherWindow.getGlassWindow()[1][3], anotherWindow.getGlassWindowCopy())) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[1][3]);
            anotherWindow.addDieToCopy(redDie2);
        }
        if(anotherWindow.canBePlaced(redDie3, anotherWindow.getGlassWindow()[2][2], anotherWindow.getGlassWindowCopy())) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[2][2]);
            anotherWindow.addDieToCopy(redDie3);
        }
        if(anotherWindow.canBePlaced(redDie4, anotherWindow.getGlassWindow()[2][4], anotherWindow.getGlassWindowCopy())) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[2][4]);
            anotherWindow.addDieToCopy(redDie4);
        }
        if(anotherWindow.canBePlaced(redDie5, anotherWindow.getGlassWindow()[3][3], anotherWindow.getGlassWindowCopy())) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[3][3]);
            anotherWindow.addDieToCopy(redDie5);
        }
        if(anotherWindow.canBePlaced(redDie6, anotherWindow.getGlassWindow()[3][1], anotherWindow.getGlassWindowCopy())) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[3][1]);
            anotherWindow.addDieToCopy(redDie6);
        }
        if(anotherWindow.canBePlaced(redDie7, anotherWindow.getGlassWindow()[2][0], anotherWindow.getGlassWindowCopy())) {
            anotherWindow.setDesiredCell(anotherWindow.getGlassWindow()[2][0]);
            anotherWindow.addDieToCopy(redDie7);
        }
        anotherWindow.overwriteOriginal();
        return anotherWindow;
    }
}
