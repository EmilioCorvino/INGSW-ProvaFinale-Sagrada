package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PublicObjectiveCardSlot;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCardsDeck;
import it.polimi.ingsw.model.cards.objective.publics.CardTestField;
import it.polimi.ingsw.model.cards.objective.publics.PublicObjectiveCardsDeck;
import it.polimi.ingsw.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreTest {
    private CommonBoard board;
    private Player player;
    private PublicObjectiveCardsDeck publicObjectiveCardsDeck;
    private PrivateObjectiveCardsDeck privateObjectiveCardsDeck;

    @Before
    public void setUp() {
        this.board = new CommonBoard();
        this.player = new Player("Korax", this.board);

        this.publicObjectiveCardsDeck = new PublicObjectiveCardsDeck();
        this.privateObjectiveCardsDeck = new PrivateObjectiveCardsDeck();
        this.publicObjectiveCardsDeck.parseDeck();
        this.privateObjectiveCardsDeck.parseDeck();

        //Normally, these would be randomly drawn, but for the sake of the test, I'll choose specific cards.
        this.player.setPrivateObjectiveCard(privateObjectiveCardsDeck.getDeck().get(2)); //Green Shades.
        this.board.getPublicObjectiveCardSlots().add(
                new PublicObjectiveCardSlot(publicObjectiveCardsDeck.getDeck().get(2))); //Coloured Diagonals.
        this.board.getPublicObjectiveCardSlots().add(
                new PublicObjectiveCardSlot(publicObjectiveCardsDeck.getDeck().get(7))); //Medium Shades.
        this.board.getPublicObjectiveCardSlots().add(
                new PublicObjectiveCardSlot(publicObjectiveCardsDeck.getDeck().get(0))); //Row Color Variety.

        //I'll set the window pattern card of the player to a precompiled one. Again, this is for testing purposes.
        this.player.setWindowPatternCard(CardTestField.precompileWindowPatternCard());
    }

    @After
    public void tearDown() {
        this.board = null;
        this.player = null;
        this.publicObjectiveCardsDeck = null;
        this.privateObjectiveCardsDeck = null;
    }

    @Test
    public void computeFinalScoreTest() {
        Score score = this.player.getScore();
        score.computeTotalScore();

        assertEquals(37, score.getTotalScore());
    }
}
