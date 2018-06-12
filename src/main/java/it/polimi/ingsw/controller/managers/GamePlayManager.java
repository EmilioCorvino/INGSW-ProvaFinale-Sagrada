package it.polimi.ingsw.controller.managers;


import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 *
 */
public class GamePlayManager extends AGameManager {

    private List<Commands> currentPlayerCommands;

    private List<Commands> waitingPlayersCommands;

    /**
     * This constructor, other than setting the controller master, also initializes the lists of commands to be shown
     * both to the player on duty ({@link #currentPlayerCommands}) and to the others ({@link #waitingPlayersCommands}).
     * @param controllerMaster main controller class.
     */
    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        List<Commands> toolCommands = new ArrayList<>();

        //Gets the tool cards commands from the model.
        for(ToolCardSlot slot: controllerMaster.getCommonBoard().getToolCardSlots()) {
            toolCommands.addAll(Arrays.asList(slot.getToolCard().getEffectBuilder().getEffects()));
        }

        this.currentPlayerCommands = new ArrayList<>(Arrays.asList(Commands.PLACEMENT, Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS, Commands.LOGOUT));

        //Inserts tool cards commands after the placement one.
        this.currentPlayerCommands.addAll(1, toolCommands);

        this.waitingPlayersCommands = new ArrayList<>(Arrays.asList(Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS, Commands.LOGOUT));
    }

    /**
     * Starts the match preparing the first list of turn order using {@link it.polimi.ingsw.model.PlayerTurnState}.
     */
    void startMatch() {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
        GameState gameState = super.getControllerMaster().getGameState();
        gameState.getTurnState().initializePlayerList(players);
        gameState.setStartPlayer(gameState.getActualPlayer());
        this.startTurn(gameState.getStartPlayer());
    }

    /**
     * This method manages the turn of the current player.
     *
     * @param currentPlayer the current player.
     */
    public void startTurn(Player currentPlayer) {
        //todo mostrare i comandi qui. Mostrare i comandi di turno al player di turno, mostrare gli altri comandi agli altri.
        //Shows the commands to the player on duty.
        IFromServerToClient currentPlayerClient =
                super.getControllerMaster().getConnectedPlayers().get(currentPlayer.getPlayerName()).getClient();
        try {
            currentPlayerClient.showCommand(this.currentPlayerCommands);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show current player commands to the player on duty", e);
            //todo suspendPlayer(currentPlayer.getPlayerName);
        }

        //If the player on duty takes too much time to perform an action, suspends him.


        //Shows the available commands to the players waiting.
        List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
        for(Player player: waitingPlayers) {
            if(!player.isSamePlayerAs(currentPlayer)) {
                IFromServerToClient waitingPlayerClient =
                        super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
                try {
                    waitingPlayerClient.showCommand(this.waitingPlayersCommands);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Impossible to show waiting players commands " +
                            "to the waiting players", e);
                    //todo suspendPlayer(player.getPlayerName);
                }
            }
        }
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

    public void showPlacementResult(Player playerColor, SetUpInformationUnit setUpInformationUnit) {
        IFromServerToClient ifstc = super.getControllerMaster().getConnectedPlayers().get(playerColor.getPlayerName()).getClient();
        try {
            ifstc.showUpdatedWp(playerColor.getPlayerName(), setUpInformationUnit);
        } catch (BrokenConnectionException br) {
            //handle broken connection
        }
    }

    /**
     * This method notifies the client any type of issue may occur in each request.
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
     * @return {@code true} if the end-turn conditions are satisfied, {@code false} otherwise.
     */
    public boolean isTurnOver() {
        //Checks if all possible moves have already been done.
        if (this.getControllerMaster().getGameState().movesTurnState())
            return true;

        List<ToolCardSlot> toolCardSlots = super.getControllerMaster().getCommonBoard().getToolCardSlots();
        int slotUsed = super.getControllerMaster().getGameState().getTurnState().getToolSlotUsed();
        int playerTokens = super.getControllerMaster().getGameState().getActualPlayer().getFavorTokens();

        //Checks if the tool card didn't imply a placement and if the player has enough favor tokens to pay for it.
        if (this.getControllerMaster().getGameState().getTurnState().isDiePlaced())
            for (ToolCardSlot slot : toolCardSlots)
                if (slot.checkTokens(playerTokens) && !slot.checkImpliesPlacement())
                    return true;

        //Checks if the tool card implied a placement.
        if (this.getControllerMaster().getGameState().getTurnState().isToolCardUsed())
            return (toolCardSlots.get(slotUsed).checkImpliesPlacement());

        //If none of the conditions ahead are verified.
        return false;
    }

    /**
     * This method is used when a player disconnects or takes too much time to complete his turn. It adds the player to
     * the {@link ControllerMaster}'s suspended players list and allows to skip his turns, considering him in the
     * final score anyway.
     * @param playerName player to suspend.
     */
    private void suspendPlayer(String playerName) {

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