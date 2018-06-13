package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.objective.publics.PublicObjectiveCardsDeck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class performs test using cards through the visitor. Only private objective cards are tested here (and in
 * {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCardTest}. The public objective cards will
 * be tested through the visitor, but in their own test classes.
 */
public class ObjectiveCardAnalyzerVisitorTest {
    private AObjectiveCard card;
    private ObjectiveCardAnalyzerVisitor visitor;
    private WindowPatternCard window;
    private PrivateObjectiveCardsDeck deckPrivates;
    private PublicObjectiveCardsDeck deckPublics;

    /**
     * Initialization of local variables and window pattern card set up.
     */
    @Before
    public void setUp() {
        this.visitor = new ObjectiveCardAnalyzerVisitor();
        this.window = new WindowPatternCard(0, 3);
        this.deckPrivates = new PrivateObjectiveCardsDeck();
        this.deckPrivates.parseDeck();

        Die greenDie1 = new Die(2, Color.GREEN);
        Die redDie1 = new Die(4, Color.RED);
        Die purpleDie = new Die(5, Color.PURPLE);
        Die yellowDie = new Die(3, Color.YELLOW);
        Die greenDie2 = new Die(4, Color.GREEN);
        Die redDie2 = new Die(6, Color.RED);
        Die redDie3 = new Die(2, Color.RED);
        Die blueDie = new Die(1, Color.BLUE);

        if(window.canBePlaced(greenDie1, window.getGlassWindow()[0][3])) {
            window.setDesiredCell(window.getGlassWindow()[0][3]);
            window.addDie(greenDie1);
        }
        if(window.canBePlaced(redDie1, window.getGlassWindow()[1][2])) {
            window.setDesiredCell(window.getGlassWindow()[1][2]);
            window.addDie(redDie1);
        }
        if(window.canBePlaced(purpleDie, window.getGlassWindow()[2][2])) {
            window.setDesiredCell(window.getGlassWindow()[2][2]);
            window.addDie(purpleDie);
        }
        if(window.canBePlaced(yellowDie, window.getGlassWindow()[2][3])) {
            window.setDesiredCell(window.getGlassWindow()[2][3]);
            window.addDie(yellowDie);
        }
        if(window.canBePlaced(greenDie2, window.getGlassWindow()[3][1])) {
            window.setDesiredCell(window.getGlassWindow()[3][1]);
            window.addDie(greenDie2);
        }
        if(window.canBePlaced(redDie2, window.getGlassWindow()[3][4])) {
            window.setDesiredCell(window.getGlassWindow()[3][4]);
            window.addDie(redDie2);
        }
        if(window.canBePlaced(redDie3, window.getGlassWindow()[3][2])) {
            window.setDesiredCell(window.getGlassWindow()[3][2]);
            window.addDie(redDie3);
        }
        if(window.canBePlaced(blueDie, window.getGlassWindow()[2][1])) {
            window.setDesiredCell(window.getGlassWindow()[2][1]);
            window.addDie(blueDie);
        }
    }

    @After
    public void tearDown() {
        this.visitor = null;
        this.window = null;
        this.deckPrivates = null;
        this.deckPublics = null;
    }

    /**
     * This test is similar to the one performed in
     * {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCardTest}, but it's done through the
     * visitor. It analyzed a window pattern card, using the "Sfumature Rosse" card.
     */
    @Test
    public void visitPrivateTest() {
        this.card = deckPrivates.getDeck().get(0);  //The RED card.
        this.card.accept(visitor, window);

        assertEquals(12, this.visitor.getScoreFromCard());

    }
}