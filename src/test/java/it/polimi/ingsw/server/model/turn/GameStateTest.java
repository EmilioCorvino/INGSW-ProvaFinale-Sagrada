package it.polimi.ingsw.server.model.turn;

import it.polimi.ingsw.server.model.CommonBoard;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameStateTest {
    private List<Player> playerList;
    private CommonBoard commonBoard;
    private GameState gameState;

    @Before
    public void setUp() {
        this.playerList = new ArrayList<>();
        this.commonBoard = new CommonBoard();
        this.playerList.add(new Player("Emilio", commonBoard));
        this.playerList.add(new Player("Gianluca", commonBoard));
        this.playerList.add(new Player("Rita", commonBoard));
        this.playerList.add(new Player("Unknown", commonBoard));
        this.gameState = new GameState();
    }

    @After
    public void tearDown() {
        this.playerList = null;
        this.commonBoard = null;
        this.gameState = null;
    }

    @Test
    public void initializePlayerListTest() {
        gameState.initializePlayerList(this.playerList);
        List<Turn> expectedTurnOrder = new ArrayList<>(Arrays.asList(new Turn(new Player("Emilio", commonBoard)),
                new Turn(new Player("Gianluca", commonBoard)), new Turn(new Player("Rita", commonBoard)),
                new Turn(new Player("Unknown", commonBoard)), new Turn(new Player("Unknown", commonBoard)),
                new Turn(new Player("Rita", commonBoard)), new Turn(new Player("Gianluca", commonBoard)),
                new Turn(new Player("Emilio", commonBoard))));

        assertEquals(expectedTurnOrder.size(), gameState.getTurnOrder().size());

        for(int i = 0; i < gameState.getTurnOrder().size(); i++) {
            assertTrue(expectedTurnOrder.get(i).getPlayer().isSamePlayerAs(gameState.getTurnOrder().get(i).getPlayer()));
        }
    }

    @Test
    public void reorderPlayerListTest() {
        this.initializePlayerListTest();
        gameState.reorderPlayerList(this.playerList);
        gameState.initializePlayerList(this.playerList);
        List<Turn> expectedTurnOrder = new ArrayList<>(Arrays.asList(new Turn(new Player("Gianluca", commonBoard)),
                new Turn(new Player("Rita", commonBoard)), new Turn(new Player("Unknown", commonBoard)),
                new Turn(new Player("Emilio", commonBoard)), new Turn(new Player("Emilio", commonBoard)),
                new Turn(new Player("Unknown", commonBoard)), new Turn(new Player("Rita", commonBoard)),
                new Turn(new Player("Gianluca", commonBoard))));

        assertEquals(expectedTurnOrder.size(), gameState.getTurnOrder().size());

        for(int i = 0; i < gameState.getTurnOrder().size(); i++) {
            assertTrue(expectedTurnOrder.get(i).getPlayer().isSamePlayerAs(gameState.getTurnOrder().get(i).getPlayer()));
        }
    }

    @Test
    public void getTurnOrderTest() {
    }

    @Test
    public void isCurrentTurnOverTest() {
    }

    @Test
    public void getActualRoundTest() {
    }

    @Test
    public void getCurrentPlayerTurnIndexTest() {
    }

    @Test
    public void resetCurrentPlayerTurnIndexTest() {
    }

    @Test
    public void incrementActualRoundTest() {
    }

    @Test
    public void incrementCurrentPlayerTurnIndexTest() {
    }

    @Test
    public void isMatchOverTest() {
    }

    @Test
    public void setMatchOverTest() {
    }

    @Test
    public void getCurrentTurnTest() {
    }

    @Test
    public void getCurrentPlayerTest() {
    }
}