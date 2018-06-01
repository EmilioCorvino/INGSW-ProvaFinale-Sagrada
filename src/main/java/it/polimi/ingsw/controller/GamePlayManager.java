package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GamePlayManager extends AGameManager {

    /**
     *
     */
    private final int NUM_ROUND = 10;

    /**
     *
     */
    private List<Player> playerList;

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
        playerList = new ArrayList<>();
        this.startPlayer = 0;
        this.currentRound = 1;
    }

    /**
     * This method creates the appropriate list of player to flow each round.
     * //TODO testing
     */
    public void initializePlayerList() {
        System.out.println("initializing players....");
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
        List<Player> supportList = new ArrayList<>();
        players.forEach(player -> {
            playerList.add(player);
            supportList.add(player);
        });

        for(int i= supportList.size()-1; i>=0; i--)
            playerList.add(supportList.get(i));
        //reOrderPlayerList();

        for(Player p : playerList)
            System.out.println(p.getPlayerName());
    }

    /**
     * This method reorders the list of the player.
     * //TODO testing
     */
    private void reOrderPlayerList() {
        Player playerToMove = playerList.get(startPlayer);
        if(startPlayer != 0) {
            playerList.remove(startPlayer );
            playerList.add(playerToMove);
        }
    }



    public void startTurn() {
        //all the view commands are blocked by default
        //for this current player advise that his/her inputs are needed
        //unlock commands for the current player
        //start the timer

        /*
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
        */
    }

    /**
     *
     */
    private void endTurn() {
        //increment current player variable
        playerList.remove(playerList.get(startPlayer));
        startPlayer++;
        //lock ex current player
    }

    /**
     * This method checks the current player.
     * @param username the color of the current player.
     * @return true if the given color matches the color of the current player.
     */
    public boolean checkCurrentPlayer(String username) {
        return username.equals(this.playerList.get(startPlayer).getPlayerName());
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
        move.executeMove(this, info);

    }

    public List<Player> getPlayerColorList() {
        return playerList;
    }

    public void setPlayerColorList(List<Player> playerColorList) {
        this.playerList = playerColorList;
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
            super.getControllerMaster().getConnectedPlayers().get(playerList.get(currentPlayer).getPlayerName()).giveProperObjectToFill(setUpInfo);
        } catch (BrokenConnectionException br) {
            //TODO handle broken connection by suspending player, using logs etc.
        }
    }


    public void showPlacementResult(Player playerColor, SetUpInformationUnit setUpInformationUnit) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        super.getControllerMaster().getConnectedPlayers().entrySet().forEach(entry -> {
            try {
                entry.getValue().showUpdatedWp(commonBoard.getPlayerMap().get(playerColor).getPlayerName(), setUpInformationUnit);

            } catch (BrokenConnectionException br) {
                //handle
            }
        });
        endTurn();
    }
}
