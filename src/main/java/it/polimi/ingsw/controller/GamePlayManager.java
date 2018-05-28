package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.IFromServerToClient;

import java.util.*;

/**
 *
 */
public class GamePlayManager extends AGameManager {

    private final int NUM_ROUND = 10;

    /**
     * The list of the player to flow during the
     */
    private final List<String> playerList;

    /**
     * The index of the current player inside playerList.
     */
    private int currentPlayer;

    /**
     *
     */
    private int currentRound;

    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        playerList = new ArrayList<>();
        this.currentPlayer = 0;
        this.currentRound = 1;
    }

    /*
    private void constructPlayerList() {
        for(Map.Entry<String, IFromServerToClient> entry: getControllerMaster().getConnectedPlayers().entrySet()) {
            playerList.add(entry.getKey());
        }
        for(int i = playerList.size() -1; i>=0; i-- )
            playerList.add(playerList.get(i));
    }
    */


    public void startTurn() {
        //all the view commands are blocked by default
        //for this current player advise that his/her inputs are needed
        //unlock commands for the current player
        //start the timer
        Timer timerTurn = new Timer();
        timerTurn.schedule(new TimerTask() {
            @Override
            public void run() {
                endTurn();

                if(currentRound == NUM_ROUND) {
                    //go to end game in  some way
                }

            }
        }, 5 * 1000);
    }

    private void endTurn() {
        //increment current player variable
        this.currentPlayer ++;
        //lock ex current player
    }




    public List<String> getPlayerList() {
        return playerList;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


}
