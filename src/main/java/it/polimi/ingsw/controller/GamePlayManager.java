package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.List;

/**
 *
 */
public class GamePlayManager extends AGameManager {


    //private static final int NUM_ROUND = 10;
    //private List<Player> playerList;
    //private int startPlayer;
    //private int currentRound;
    //private int currentPlayer;


    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
    }




    /**
     * This method manages the turn of the current player.
     *
     * @param currentPlayer the current player.
     */

    public void startTurn(Player currentPlayer) {
        //GIAN ???
        /*
        try {
            super.getControllerMaster().getConnectedPlayers().get(currentPlayer.getPlayerName()).getClient().setMyTurn(true);
            for (Player p : super.getControllerMaster().getGameState().g) {
                if (!p.isSamePlayerAs(playerList.get(this.currentPlayer)))
                    super.getControllerMaster().getConnectedPlayers().get(p.getPlayerName()).getClient().setMyTurn(false);
            }
            super.getControllerMaster().getConnectedPlayers().get(currentPlayer.getPlayerName()).getClient().showCommand();
        } catch (BrokenConnectionException br) {
            //handle broken connection
        }
        */
    }

    /**
     * //provisional
     */
    private void endTurn() {

    }

    /**
     * This method checks the current player.
     *
     * @param username the color of the current player.
     * @return true if the given color matches the color of the current player.
     */
    public boolean checkCurrentPlayer(String username) {
        Player player = super.getControllerMaster().getGameState().getActualPlayer();
        return username.equals(player.getPlayerName());
    }


    /**
     * @param info
     */
    public void performMove(SetUpInformationUnit info) {
        IMove move = new DiePlacementMove();
        move.executeMove(this, info);

    }

    public void givePlayerObjectTofill() {
        SetUpInformationUnit setUpInfo = new SetUpInformationUnit();
        Player player = super.getControllerMaster().getGameState().getActualPlayer();
        try {
            super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient().giveProperObjectToFill(setUpInfo);
        } catch (BrokenConnectionException br) {
            //TODO handle broken connection by suspending player, using logs etc.
        }
    }


    public void showPlacementResult(Player playerColor, SetUpInformationUnit setUpInformationUnit) {
        IFromServerToClient ifstc = super.getControllerMaster().getConnectedPlayers().get(playerColor.getPlayerName()).getClient();
        try {
            System.out.println("me la mostri o no sta mappa " + playerColor.getPlayerName());
            ifstc.showUpdatedWp(playerColor.getPlayerName(), setUpInformationUnit);
        } catch (BrokenConnectionException br) {
            //handle broken connection
        }
    }

    /**
     * Thi method notifies the client any type of issue may occur in each request.
     *
     * @param message the message to tell.
     */

    public void showNotification(String message) {
        Player player = super.getControllerMaster().getGameState().getActualPlayer();
        IFromServerToClient iFromServerToClient = super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
        try {
            iFromServerToClient.showNotice(message);
        } catch (BrokenConnectionException br) {
            //handle broken connection.
        }
    }

    /**
     * This method checks if are satisfied the right conditions to move to the next player.
     * @return true if the end-turn conditions are satisfied, false otherwise.
     */
    public boolean checkEndTurn() {

        if (this.getControllerMaster().getGameState().movesTurnState())
            return true;

        List<ToolCardSlot> toolCardSlots = super.getControllerMaster().getCommonBoard().getToolCardSlots();
        int slotUsed = super.getControllerMaster().getGameState().getTurnState().getToolSlotUsed();
        int playerTokens = super.getControllerMaster().getGameState().getActualPlayer().getFavorTokens();

        if (this.getControllerMaster().getGameState().getTurnState().isPlacedDie())
            for (ToolCardSlot slot : toolCardSlots)
                if (slot.checkTokens(playerTokens) && !slot.checkImpliesPlacement())
                    return true;

        if (this.getControllerMaster().getGameState().getTurnState().isUsedToolCard())
            if(toolCardSlots.get(slotUsed).checkImpliesPlacement())
                return true;
        return false;
    }
}

       /*
    private void reOrderPlayerList() {
        Player playerToMove = playerList.get(startPlayer);
        if(startPlayer != 0) {
            playerList.remove(startPlayer );
            playerList.add(playerToMove);
        }
    }
    */

        /*
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
        //super.getControllerMaster().startTurnPlayer(playerList.get(currentPlayer));
        startMatch();
    }
    */

         /*
        playerList = new ArrayList<>();
        this.startPlayer = 0;
        this.currentRound = 1;
        */
