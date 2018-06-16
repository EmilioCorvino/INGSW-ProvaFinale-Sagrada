package it.polimi.ingsw.model.cards.objective.privates;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.AObjectiveCardsDeck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The tests are made assuming the card are in the deck always in the same order (which is always the case,
 * since the parsing of an array is executed in order) and that this order is known
 * {@link cards/privateObjectiveCards.json}.
 * It's the draw method from AObjectiveCardsDeck that provides the "shuffling" by retrieving a random card
 * each time.
 * @see AObjectiveCardsDeck
 */
public class PrivateObjectiveCardTest {
    private PrivateObjectiveCard card0;
    private PrivateObjectiveCard card1;
    private PrivateObjectiveCard card2;
    private PrivateObjectiveCard card3;
    private PrivateObjectiveCard card4;

    /**
     * Parses the deck of Private Objective Cards
     */
    @Before
    public void setUp() {
        PrivateObjectiveCardsDeck deck = new PrivateObjectiveCardsDeck();
        deck.parseDeck();
        card0 = deck.getDeck().get(0);
        card1 = deck.getDeck().get(1);
        card2 = deck.getDeck().get(2);
        card3 = deck.getDeck().get(3);
        card4 = deck.getDeck().get(4);
    }

    @After
    public void tearDown() {
        card0 = null;
        card1 = null;
        card2 = null;
        card3 = null;
        card4 = null;
    }

    /**
     * Checks if the names are coherent with the ones in the file.
     */
    @Test
    public void getNameTest() {
        assertEquals("Sfumature Rosse", card0.getName());
        assertEquals("Sfumature Gialle", card1.getName());
        assertEquals("Sfumature Verdi", card2.getName());
        assertEquals("Sfumature Blu", card3.getName());
        assertEquals("Sfumature Viola", card4.getName());
    }

    /**
     * Checks if the colors are coherent with the ones in the file.
     */
    @Test
    public void getCardColorTest() {
        assertEquals(Color.RED, card0.getCardColor());
        assertEquals(Color.YELLOW, card1.getCardColor());
        assertEquals(Color.GREEN, card2.getCardColor());
        assertEquals(Color.BLUE, card3.getCardColor());
        assertEquals(Color.PURPLE, card4.getCardColor());
    }

    /**
     * Checks if the descriptions are coherent with the ones in the file.
     */
    @Test
    public void getDescriptionTest() {
        assertEquals("Somma dei valori su tutti i dadi rossi", card0.getDescription());
        assertEquals("Somma dei valori su tutti i dadi gialli", card1.getDescription());
        assertEquals("Somma dei valori su tutti i dadi verdi", card2.getDescription());
        assertEquals("Somma dei valori su tutti i dadi blu", card3.getDescription());
        assertEquals("Somma dei valori su tutti i dadi viola", card4.getDescription());
    }

    /**
     * A dummy {@link WindowPatternCard} is built, with no restrictions. The test is done using the card
     * "Sfumature Viola" in a {@link WindowPatternCard} in which there are 3 purple dice and 1 die for each
     * remaining color.
     * Dice are placed respecting the standard placement rules (canBePlaced always returns true in this case).
     */
    @Test
    public void analyzeWindowPatternCardTest() {
        WindowPatternCard window = new WindowPatternCard(0, 2);
        Die purpleDie1 = new Die(4, Color.PURPLE);
        Die purpleDie2 = new Die(3, Color.PURPLE);
        Die purpleDie3 = new Die(6, Color.PURPLE);
        Die redDie = new Die(1, Color.RED);
        Die yellowDie = new Die(5, Color.YELLOW);
        Die greenDie = new Die(6, Color.GREEN);
        Die blueDie = new Die(4, Color.BLUE);

        if(window.canBePlaced(purpleDie1, window.getGlassWindow()[0][0], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[0][0]);
            window.addDie(purpleDie1);
        }
        if(window.canBePlaced(purpleDie2, window.getGlassWindow()[1][1], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[1][1]);
            window.addDie(purpleDie2);
        }
        if(window.canBePlaced(redDie, window.getGlassWindow()[0][1], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[0][1]);
            window.addDie(redDie);
        }
        if(window.canBePlaced(yellowDie, window.getGlassWindow()[1][0], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[1][0]);
            window.addDie(yellowDie);
        }
        if(window.canBePlaced(greenDie, window.getGlassWindow()[2][1], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[2][1]);
            window.addDie(greenDie);
        }
        if(window.canBePlaced(blueDie, window.getGlassWindow()[3][2], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[3][2]);
            window.addDie(blueDie);
        }
        if(window.canBePlaced(purpleDie3, window.getGlassWindow()[2][3], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[2][3]);
            window.addDie(purpleDie3);
        }

        assertEquals(13, card4.analyzeWindowPatternCard(window)); //4+3+6=13
    }

    /**
     * The equals compares only the ids. Only one card, it's the same for the others.
     */
    @Test
    public void equalsTest() {
        PrivateObjectiveCard successCard = new PrivateObjectiveCard(102, "Sfumature Verdi", Color.GREEN,
                "Somma dei valori su tutti i dadi verdi");
        PrivateObjectiveCard failCard = new PrivateObjectiveCard(142, "In realtà non c'è", Color.RED,
                "Lorem ipsum");

        assertTrue(successCard.equals(card2));
        assertFalse(failCard.equals(card0));
    }

    /**
     * Tested for one card only, as an example.
     */
    @Test
    public void toStringTest() {
        assertEquals("Private Objective Card: Sfumature Gialle--> Somma dei valori su tutti i dadi gialli\n",
                card1.toString());
    }
}