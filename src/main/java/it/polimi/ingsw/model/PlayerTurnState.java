package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the state of the turns of a match.
 */
public class PlayerTurnState {

    /**
     * This attribute represents the list of the player in a match in a correct order.
     */
    private List<Player> turnOrder;

    /**
     * This attribute represents the index of the current player.
     */
    private int currentPlayerIndex;

    /**
     * This attribute indicates if the current player has placed a die.
     */
    private boolean placedDie;

    /**
     * This attribute indicates if the current player has used a tool card.
     */
    private boolean usedToolCard;

    /**
     * This attribute indicates the id of the slot containing the tool card the player has used.
     */
    private int toolSlotUsed;



    //TODO complete with other information

    public PlayerTurnState() {
        turnOrder = new ArrayList<>();
        currentPlayerIndex = 0;
        //TODO
    }

    /**
     * This method initializes the list of the player in a specific turn.
     * @param players the list of the players that participate into a match.
     */
    public void initializePlayerList(List<Player> players) {
        List<Player> supportList = new ArrayList<>();

        for(Player p : players) {
            turnOrder.add(p);
            supportList.add(p);
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

    }

    //Implementare una politica di inserimento dei giocatori
    //todo spostare i due metodi che creano la lista e la riordinano.

    /**
     * This method returns the next player.
     * @return the next player.
     */
    public Player getNext() {
        if(currentPlayerIndex < turnOrder.size())
            return turnOrder.get(currentPlayerIndex + 1);

        return turnOrder.get(currentPlayerIndex);
    }

    public boolean turnCompleted() {
        return this.placedDie && this.usedToolCard;
    }

    /**
     * This method returns the current player of the list.
     * @return the actual player.
     */
    public Player getActual() {
        return turnOrder.get(currentPlayerIndex);
    }

    public List<Player> getAllPlayer() {
        //TODO creare una nuova lista uguale a turnOrder ma diversa
        return null;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public boolean isPlacedDie() {
        return placedDie;
    }

    public void setPlacedDie(boolean placedDie) {
        this.placedDie = placedDie;
    }

    public boolean isUsedToolCard() {
        return usedToolCard;
    }

    public void setUsedToolCard(boolean usedToolCard) {
        this.usedToolCard = usedToolCard;
    }

    public int getToolSlotUsed() {
        return toolSlotUsed;
    }

    public void setToolSlotUsed(int toolSlotUsed) {
        this.toolSlotUsed = toolSlotUsed;
    }
}