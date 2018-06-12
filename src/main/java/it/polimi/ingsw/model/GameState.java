package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

/**
 * This class represents a specific state of the game.
 */
public class GameState {

    /**
     * The number of rounds tha must be played before the end of the game.
     */
    private static final int NUM_ROUND = 10;

    /**
     * This attribute represents the state of the turn of the current player.
     */
    private PlayerTurnState turnState;

    /**
     * This attribute represents the index of the current round of the match.
     */
    private int actualRound;

    /**
     * This attribute represent the initial player of a round.
     */
    private Player startPlayer;



    public GameState() {
        this.turnState = new PlayerTurnState();
        this.actualRound = 1;
    }

    public boolean movesTurnState() {
        return this.turnState.turnCompleted();
    }

    public void setNewRound() {
        this.actualRound++;
    }

    public Player getActualPlayer() {
        return turnState.getActual();
    }

    public static int getNumRound() {
        return NUM_ROUND;
    }

    public PlayerTurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(PlayerTurnState turnState) {
        this.turnState = turnState;
    }

    public int getActualRound() {
        return actualRound;
    }

    public void setActualRound(int actualRound) {
        this.actualRound = actualRound;
    }

    public Player getStartPlayer() {
        return startPlayer;
    }

    public void setStartPlayer(Player startPlayer) {
        this.startPlayer = startPlayer;
    }

}
