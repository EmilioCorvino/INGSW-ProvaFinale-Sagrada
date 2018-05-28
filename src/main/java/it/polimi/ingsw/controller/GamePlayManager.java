package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.PlacementRestrictionEffect;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.network.PlayerColor;

import java.util.*;

/**
 *
 */
public class GamePlayManager extends AGameManager {

    private final int NUM_ROUND = 10;

    private IMove move;

    /**
     *
     */
    private List<PlayerColor> playerColorList;

    /**
     * The index of the current player inside playerList.
     */
    private int currentPlayer;

    /**
     * The index of the current round.
     */
    private int currentRound;

    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        playerColorList = new LinkedList<>();
        this.currentPlayer = 0;
        this.currentRound = 1;
        initializePlayerList();
    }

    /**
     * This method creates the appropriate list of player to flow each round.
     * //TODO testing
     */
    public void initializePlayerList() {
        Set<PlayerColor> mapKeys = getControllerMaster().getCommonBoard().getPlayerMap().keySet();
        List<PlayerColor> supportList = new ArrayList<>();
        mapKeys.forEach(color -> {
            supportList.add(color);
            playerColorList.add(color);
        });
        reOrderPlayerList();
        for(int i= supportList.size()-1; i>=0; i--)
            playerColorList.add(supportList.get(i));
    }

    /**
     * This method reorders the list of the player.
     * //TODO testing
     */
    public void reOrderPlayerList() {
        PlayerColor colorToMove = playerColorList.get(currentPlayer-1);
        playerColorList.remove(currentPlayer-1);
        playerColorList.add(colorToMove);
    }


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
                //TODO adjust this
                if(currentRound == NUM_ROUND) {
                    timerTurn.cancel();
                    //go to end game in  some way
                }

            }
        }, 5 * 1000);
    }

    /**
     *
     */
    private void endTurn() {
        //increment current player variable
        playerColorList.remove(playerColorList.get(currentPlayer));
        currentPlayer++;
        //lock ex current player
    }

    /**
     *
     * @param playerColor
     * @return
     */
    public boolean checkCurrentPlayer(PlayerColor playerColor) {
        return playerColor.equals(this.playerColorList.get(currentPlayer));
    }


    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    /**
     *
     * @param playerColor
     */
    public void analyzeDefaultMoveRequest(PlayerColor playerColor) {
        if(!playerColor.equals(playerColorList.get(currentPlayer)))
            System.out.println("it is not your turn!");
        else {
            move = new PlacementRestrictionEffect();
        }
    }

    /**
     *
     * @param info
     */
    public void performMove(SetUpInformationUnit info) {
        move.executeMove(playerColorList.get(currentPlayer), this, info, super.getControllerMaster().getCommonBoard().getDraftPool());

    }


}
