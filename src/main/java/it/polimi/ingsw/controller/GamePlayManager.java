package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.network.PlayerColor;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

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
     * The index of the start player inside playerList. It is updated each round.
     */
    private int startPlayer;

    /**
     * The index of the current round.
     */
    private int currentRound;

    /**
     * The index of the current player inside playerList.
     */
    private int currentPlayer;

    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        playerColorList = new LinkedList<>();
        this.startPlayer = 0;
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
    private void reOrderPlayerList() {
        PlayerColor colorToMove = playerColorList.get(startPlayer -1);
        if(startPlayer != 0) {
            playerColorList.remove(startPlayer -1);
            playerColorList.add(colorToMove);
        }
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
        playerColorList.remove(playerColorList.get(startPlayer));
        startPlayer++;
        //lock ex current player
    }

    /**
     * This method checks the current player.
     * @param playerColor the color of the current player.
     * @return true if the given color matches the color of the current player.
     */
    public boolean checkCurrentPlayer(PlayerColor playerColor) {
        return playerColor.equals(this.playerColorList.get(startPlayer));
    }


    public int getStartPlayer() {
        return startPlayer;
    }

    public void setStartPlayer(int startPlayer) {
        this.startPlayer = startPlayer;
    }

    /**
     *
     * @param info
     */
    public void performMove(SetUpInformationUnit info) {
        IMove move = new DiePlacementMove();
        move.executeMove(playerColorList.get(startPlayer), this, info);

    }

    public List<PlayerColor> getPlayerColorList() {
        return playerColorList;
    }

    public void setPlayerColorList(List<PlayerColor> playerColorList) {
        this.playerColorList = playerColorList;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void givePlayerObjectTofill() {
        SetUpInformationUnit setUpInfo = new SetUpInformationUnit();
        try {
            super.getControllerMaster().getConnectedPlayers().get(playerColorList.get(currentPlayer)).giveProperObjectToFill(setUpInfo);
        } catch (BrokenConnectionException br) {
            //TODO handle broken connection by suspending player, using logs etc.
        }
    }
}
