package it.polimi.ingsw.controller.managers;


import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.turn.GameState;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.turn.Turn;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;

/**
 *
 */
public class GamePlayManager extends AGameManager {

    /**
     * List of commands that the player on duty can preform.
     */
    private List<Commands> currentPlayerCommands;

    /**
     * List of commands that the waiting players can perform.
     */
    private List<Commands> waitingPlayersCommands;

    /**
     * Timer representing how much time each player has to complete his turn.
     */
    private Timer timer;

    /**
     * Path of the file containing the amount of time to wait.
     */
    private static final String TIMER_FILE = "./src/main/java/it/polimi/ingsw/utils/config/turnTimer";

    /**
     * Timer to use in case the loading from file fails. Value is in milliseconds.
     */
    private static final long BACK_UP_TIMER = 30000;

    /**
     * This constructor, other than setting the controller master, also initializes the lists of commands to be shown
     * both to the player on duty ({@link #currentPlayerCommands}) and to the others ({@link #waitingPlayersCommands}).
     * @param controllerMaster main controller class.
     */
    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        this.currentPlayerCommands = new ArrayList<>();

        //Gets the tool cards commands from the model.
        for(ToolCardSlot slot: controllerMaster.getCommonBoard().getToolCardSlots()) {
            this.currentPlayerCommands.addAll(Arrays.asList(slot.getToolCard().getEffectBuilder().getEffects()));
        }

        this.currentPlayerCommands.addAll(Arrays.asList(Commands.PLACEMENT, Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS, Commands.LOGOUT));

        this.waitingPlayersCommands = new ArrayList<>(Arrays.asList(Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS, Commands.LOGOUT));
    }

    /**
     * Starts the match preparing the first list of turn order using {@link Turn}.
     */
    void startMatch() {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
        GameState gameState = super.getControllerMaster().getGameState();
        gameState.initializePlayerList(players);
        this.startTurn(gameState.getCurrentTurn());
    }

    /**
     * This method manages the turn of the current player.
     *
     * @param currentPlayerTurn the turn of the current player.
     */
    public void startTurn(Turn currentPlayerTurn) {
        //todo mostrare i comandi qui. Mostrare i comandi di turno al player di turno, mostrare gli altri comandi agli altri.
        //Shows the commands to the player on duty.
        IFromServerToClient currentPlayerClient =
                super.getControllerMaster().getConnectedPlayers().get(currentPlayerTurn.getPlayer().getPlayerName()).getClient();
        try {
            currentPlayerClient.showCommand(this.currentPlayerCommands);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show current player commands to the player on duty", e);
            //todo super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName);
        }
        this.startTimer(currentPlayerTurn);


        //Shows the available commands to the players waiting.
        List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
        for(Player player: waitingPlayers) {
            if(!player.isSamePlayerAs(currentPlayerTurn.getPlayer())) {
                IFromServerToClient waitingPlayerClient =
                        super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
                try {
                    waitingPlayerClient.showCommand(this.waitingPlayersCommands);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Impossible to show waiting players commands " +
                            "to the waiting players", e);
                    //todo super.getControllerMaster().suspendPlayer(player.getPlayerName);
                }
            }
        }
    }

    /**
     * Starts the timer of the turn. If it can't be loaded from file, a back up value is used.
     * @param turn turn of the player to suspend in case his turn isn't over when the timer expires. It has a reference
     *             to said player.
     */
    private void startTimer(Turn turn) {
        this.timer = new Timer();

        //Back up value.
        long timeOut = BACK_UP_TIMER;

        //Value read from file. If the loading is successful, it overwrites the back up.
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(TIMER_FILE)))) {
            timeOut = Long.parseLong(reader.readLine());
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to load the turn timer from file.", e);
        }
        SagradaLogger.log(Level.INFO, "Turn timer is started");

        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SagradaLogger.log(Level.WARNING, "Turn timer is expired");
                if(!turn.isTurnCompleted()) {
                    getControllerMaster().suspendPlayer(turn.getPlayer().getPlayerName());
                }
                timer.cancel();
            }
        }, timeOut);
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
        Player player = super.getControllerMaster().getGameState().getCurrentPlayer();
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
        Player player = super.getControllerMaster().getGameState().getCurrentPlayer();
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
    //todo maybe remove this.
    /*public boolean isTurnOver(Turn turn) {
        //Checks if all possible moves have already been done.
        if (this.getControllerMaster().getGameState().movesTurnState())
            return true;

        List<ToolCardSlot> toolCardSlots = super.getControllerMaster().getCommonBoard().getToolCardSlots();
        int slotUsed = super.getControllerMaster().getGameState().getTurnOrder().getToolSlotUsed();
        int playerTokens = super.getControllerMaster().getGameState().getActualPlayer().getFavorTokens();

        //Checks if the tool card didn't imply a placement and if the player has enough favor tokens to pay for it.
        if (this.getControllerMaster().getGameState().getTurnOrder().isDiePlaced())
            for (ToolCardSlot slot : toolCardSlots)
                if (slot.checkTokens(playerTokens) && !slot.checkImpliesPlacement())
                    return true;

        //Checks if the tool card implied a placement.
        if (this.getControllerMaster().getGameState().getTurnOrder().isToolCardUsed())
            return (toolCardSlots.get(slotUsed).checkImpliesPlacement());

        //If none of the conditions ahead are verified.
        return false;
    }*/
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
