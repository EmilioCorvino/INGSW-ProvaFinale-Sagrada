package it.polimi.ingsw.model.turn;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private List<Turn> turnOrder;

    /**
     * This attribute represents the index of the current round of the match.
     */
    private int actualRound;

    /**
     * This attribute represent the initial player of a round.
     */
    private int currentPlayerTurnIndex = 0;

    public GameState() {
        this.turnOrder = new ArrayList<>();
        this.actualRound = 1;
    }

    /**
     * This method initializes the list of the player in a specific turn.
     * @param players the list of the players that participate into a match.
     */
    public void initializePlayerList(List<Player> players) {
        List<Turn> supportList = new ArrayList<>();

        for(Player p : players) {
            turnOrder.add(new Turn(p));
            supportList.add(new Turn(p));
        }

        for(int i= supportList.size()-1; i>=0; i--)
            turnOrder.add(supportList.get(i));
    }

    /**
     * This method changes the order of the list of player participating to a match.
     * @param players the list which order will be changed.
     */
    public void reorderPlayerList(List<Player> players) {
        Collections.rotate(players, players.size() - 1);
        this.initializePlayerList(players);
    }

    public List<Turn> getTurnOrder() {
        return this.turnOrder;
    }

    //Implementare una politica di inserimento dei giocatori


    /*/**
     * This method returns the next player.
     * @return the next player.
     *
    public Turn getNext() {
        if(currentPlayerIndex < turnOrder.size())
            return turnOrder.get(currentPlayerIndex + 1);

        return turnOrder.get(currentPlayerIndex);
    }*/

    public boolean movesTurnState() {
        return this.turnOrder.get(currentPlayerTurnIndex).isTurnCompleted();
    }

    public void setNewRound() {
        this.actualRound++;
    }

    public static int getNumRound() {
        return NUM_ROUND;
    }

    public int getActualRound() {
        return actualRound;
    }

    public void setActualRound(int actualRound) {
        this.actualRound = actualRound;
    }

    public int getCurrentPlayerTurnIndex() {
        return currentPlayerTurnIndex;
    }

    public void setCurrentPlayerTurnIndex(int currentPlayerTurnIndex) {
        this.currentPlayerTurnIndex = currentPlayerTurnIndex;
    }

    public Turn getCurrentTurn() {
        return this.turnOrder.get(this.currentPlayerTurnIndex);
    }

    public Player getCurrentPlayer() {
        return this.turnOrder.get(this.currentPlayerTurnIndex).getPlayer();
    }

}
