package it.polimi.ingsw.model.cards.objective.publics;

import it.polimi.ingsw.model.cards.objective.AObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.objective.ObjectiveCardAnalyzerVisitor;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;

/**
 * The tests on cards are made assuming the cards are in the deck always in the same order (which is always the case,
 * since the parsing of an array is executed in order) and that this order is known:
 * {@link cards}.
 * It's the draw method from AObjectiveCardsDeck that provides the "shuffling" by retrieving a random card
 * each time.
 * @see AObjectiveCardsDeck
 */
public abstract class CardTestField {
    PublicObjectiveCardsDeck deck;
    APublicObjectiveCard card;
    WindowPatternCard window;
    ObjectiveCardAnalyzerVisitor visitor;

    /**
     * Initializes the attributes, parses the Public Objective Cards and sets up the Window Pattern CardTestField.
     */
    public void setUp() {
        this.deck = new PublicObjectiveCardsDeck();
        this.deck.parseDeck();
        this.window = precompileWindowPatternCard();
        this.visitor = new ObjectiveCardAnalyzerVisitor();


    }

    public static WindowPatternCard precompileWindowPatternCard() {
        WindowPatternCard window = new WindowPatternCard(0, 3);

        Die greenDie1 = new Die(2, Color.GREEN);
        Die redDie1 = new Die(4, Color.RED);
        Die purpleDie1 = new Die(5, Color.PURPLE);
        Die yellowDie1 = new Die(3, Color.YELLOW);
        Die greenDie2 = new Die(4, Color.GREEN);
        Die redDie2 = new Die(6, Color.RED);
        Die redDie3 = new Die(2, Color.RED);
        Die blueDie1 = new Die(1, Color.BLUE);
        Die redDie4 = new Die(4, Color.RED);
        Die greenDie3 = new Die(5, Color.GREEN);
        Die blueDie2 = new Die(1, Color.BLUE);
        Die redDie5 = new Die(5, Color.RED);
        Die yellowDie2 = new Die(3, Color.YELLOW);
        Die purpleDie2 = new Die(6, Color.PURPLE);
        Die blueDie3 = new Die(2, Color.BLUE);
        Die purpleDie3 = new Die(6, Color.PURPLE);
        Die greenDie4 = new Die(4, Color.GREEN);

        if(window.canBePlaced(greenDie1, window.getGlassWindow()[0][3], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[0][3]);
            window.addDie(greenDie1);
        }
        if(window.canBePlaced(redDie1, window.getGlassWindow()[1][2], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[1][2]);
            window.addDie(redDie1);
        }
        if(window.canBePlaced(purpleDie1, window.getGlassWindow()[2][2], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[2][2]);
            window.addDie(purpleDie1);
        }
        if(window.canBePlaced(yellowDie1, window.getGlassWindow()[2][3], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[2][3]);
            window.addDie(yellowDie1);
        }
        if(window.canBePlaced(greenDie2, window.getGlassWindow()[3][1], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[3][1]);
            window.addDie(greenDie2);
        }
        if(window.canBePlaced(redDie2, window.getGlassWindow()[3][4], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[3][4]);
            window.addDie(redDie2);
        }
        if(window.canBePlaced(redDie3, window.getGlassWindow()[3][2], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[3][2]);
            window.addDie(redDie3);
        }
        if(window.canBePlaced(blueDie1, window.getGlassWindow()[2][1], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[2][1]);
            window.addDie(blueDie1);
        }
        if(window.canBePlaced(redDie4, window.getGlassWindow()[2][0], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[2][0]);
            window.addDie(redDie4);
        }
        if(window.canBePlaced(greenDie1, window.getGlassWindow()[0][3], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[0][3]);
            window.addDie(greenDie1);
        }
        if(window.canBePlaced(greenDie3, window.getGlassWindow()[2][4], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[2][4]);
            window.addDie(greenDie3);
        }
        if(window.canBePlaced(blueDie2, window.getGlassWindow()[0][2], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[0][2]);
            window.addDie(blueDie2);
        }
        if(window.canBePlaced(redDie5, window.getGlassWindow()[0][1], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[0][1]);
            window.addDie(redDie5);
        }
        if(window.canBePlaced(yellowDie2, window.getGlassWindow()[0][0], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[0][0]);
            window.addDie(yellowDie2);
        }
        if(window.canBePlaced(purpleDie2, window.getGlassWindow()[0][4], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[0][4]);
            window.addDie(purpleDie2);
        }
        if(window.canBePlaced(blueDie3, window.getGlassWindow()[1][0], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[1][0]);
            window.addDie(blueDie3);
        }
        if(window.canBePlaced(purpleDie3, window.getGlassWindow()[3][0], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[3][0]);
            window.addDie(purpleDie3);
        }
        if(window.canBePlaced(greenDie4, window.getGlassWindow()[3][3], window.getGlassWindow())) {
            window.setDesiredCell(window.getGlassWindow()[3][3]);
            window.addDie(greenDie4);
        }
        return window;
    }

    public void tearDown() {
        this.deck = null;
        this.card = null;
        this.window = null;
        this.visitor = null;
    }
}
