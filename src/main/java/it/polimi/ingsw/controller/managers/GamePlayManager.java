package it.polimi.ingsw.controller.managers;


import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.cards.tool.ToolCard;
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
    private static final long BACK_UP_TIMER = 3000;

    /**
     * This constructor, other than setting the controller master, also initializes the lists of commands to be shown
     * both to the player on duty ({@link #currentPlayerCommands}) and to the others ({@link #waitingPlayersCommands}).
     * @param controllerMaster main controller class.
     */
    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        this.currentPlayerCommands = new ArrayList<>();

        //Gets the tool cards commands from the model.
        /*for(ToolCardSlot slot: controllerMaster.getCommonBoard().getToolCardSlots()) {
            this.currentPlayerCommands.addAll(Arrays.asList(slot.getToolCard().getEffectBuilder().getEffects()));
        }*/

        this.currentPlayerCommands.addAll(Arrays.asList(Commands.PLACEMENT, Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS,
                Commands.END_TURN, Commands.LOGOUT));

        this.waitingPlayersCommands = new ArrayList<>(Arrays.asList(Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS, Commands.LOGOUT));
    }

    /**
     * Starts a round, preparing the turn order.
     */
    void startRound() {
        GameState gameState = super.getControllerMaster().getGameState();

        if(!gameState.isMatchOver()) {
            //Reorders the list of players each round a part from the first.
            List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
            if (gameState.getActualRound() > 1) {
                gameState.reorderPlayerList(players);
            }
            gameState.initializePlayerList(players);
            this.startTurn(gameState.getCurrentTurn());
        } else {
            //todo fix this.
            ((EndGameManager)super.getControllerMaster().getEndGameManager()).computeRank();
        }
    }

    /**
     * This method manages the turn of the current player.
     *
     * @param currentPlayerTurn the turn of the current player.
     */
    private void startTurn(Turn currentPlayerTurn) {

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
     *             to said {@link Player}.
     */
    private void startTimer(Turn turn) {
        this.timer = new Timer();

        //Back up value.
        long timeOut = BACK_UP_TIMER;

        //Value read from file. If the loading is successful, it overwrites the back up.
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(TIMER_FILE)))) {
            timeOut = Long.parseLong(reader.readLine());
            SagradaLogger.log(Level.CONFIG, "Timer successfully loaded from file. Its value is: " + timeOut);
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
                    endTurn();
                }
                timer.cancel();
            }
        }, timeOut);
    }

    /**
     * Ends the turn, incrementing the index of the current player in the turn order list at {@link GameState}.
     * Then starts a new turn.
     */
    private void endTurn() {
        GameState gameState = super.getControllerMaster().getGameState();

        //If the round is not over, proceed to the next turn.
        if(gameState.getCurrentPlayerTurnIndex() < gameState.getTurnOrder().size() - 1) {
            gameState.incrementCurrentPlayerTurnIndex();
            this.startTurn(gameState.getCurrentTurn());
        }

        //If the round is over, start a new one, unless it was the last one. In that case, report that the match is over.
        else {
            gameState.resetCurrentPlayerTurnIndex();
            if(gameState.getActualRound() < GameState.LAST_ROUND) {
                gameState.incrementActualRound();
            } else {
                gameState.setMatchOver(true);
            }
            startRound();
        }
    }

    /**
     * This method checks if the current player is the same one as the one making the request.
     * @param username the color of the current player.
     * @return true if the given color matches the color of the current player.
     */
    private boolean isRequestFromCurrentPlayer(String username) {
        Player player = super.getControllerMaster().getGameState().getCurrentPlayer();
        return username.equals(player.getPlayerName());
    }

    /**
     * @param info
     * @param playerName
     */
    public void performDefaultMove(SetUpInformationUnit info, String playerName) {
        GameState gameState = super.getControllerMaster().getGameState();
        IFromServerToClient currentPlayerClient =
                super.getControllerMaster().getConnectedPlayers().get(playerName).getClient();

        //Checks if the request comes from the current player. If not, shows the player the commands of a waiting player.
        //This is just a security measure, it should not be possible to be in this situation.
        this.handleIllegalStateRequests(playerName);

        if(!gameState.getCurrentTurn().isTurnCompleted()) {

            //If the player has already placed a die, tells him and shows him a new appropriate list of commands.
            if (gameState.getCurrentTurn().isDiePlaced()) {
                try {
                    this.showCommandsWithoutPlacement(currentPlayerClient);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Connection with the client crashed while performing a " +
                            "default move", e);
                    //todo super.getControllerMaster().suspendPlayer(playerName);
                }
            } else {
                IMove move = new DiePlacementMove();
                move.executeMove(this, info);
            }
        } else {
            this.endTurn();
        }
    }

    /**
     * Shows the result of the default placement move to the player on duty and the waiting players.
     * @param currentPlayer player on duty
     * @param wpSetUpInformationUnit information used by the view to update
     *                               the {@link it.polimi.ingsw.model.die.diecontainers.WindowPatternCard}
     * @param draftSetUpInformationUnit information used by the view to update
     *                                  the {@link it.polimi.ingsw.model.die.diecontainers.DiceDraftPool}
     */
    public void showPlacementResult(Player currentPlayer, SetUpInformationUnit wpSetUpInformationUnit,
                                    SetUpInformationUnit draftSetUpInformationUnit) {
        GameState gameState = super.getControllerMaster().getGameState();
        gameState.getCurrentTurn().setDiePlaced(true);

        //Update the board of the player on duty.
        IFromServerToClient client = super.getControllerMaster().getConnectedPlayers().get(
                currentPlayer.getPlayerName()).getClient();
        try {
            client.addOnOwnWp(wpSetUpInformationUnit);
            client.removeOnDraft(draftSetUpInformationUnit);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to update current player wp and draft pool", e);
            //todo super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName());
        }

        //Checks if the player has done everything he could. If he did, ends his turn; if not, shows him the commands.
        //The commands shown are relative to what the player can still do.
        if(!gameState.isCurrentTurnOver()) {
            try {
                client.showNotice("Puoi ancora usare una Carta Strumento che non impplichi il piazzamento di un dado.");
                this.showCommandsWithoutTools(client);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to show the player updated commands", e);
                //todo super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName());
            }
        } else {
            this.endTurn();
        }

        //Update the board of the waiting players.
        List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
        for(Player p: waitingPlayers) {
            if(!p.isSamePlayerAs(currentPlayer)) {
                IFromServerToClient waitingPlayerClient =
                        super.getControllerMaster().getConnectedPlayers().get(p.getPlayerName()).getClient();
                try {
                    waitingPlayerClient.addOnOtherPlayerWp(currentPlayer.getPlayerName(), wpSetUpInformationUnit);
                    waitingPlayerClient.removeOnDraft(draftSetUpInformationUnit);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Impossible to update waiting players wp and draft pool", e);
                    //todo super.getControllerMaster().suspendPlayer(p.getPlayerName);
                }
            }
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
            //todo super.getControllerMaster().suspendPlayer(player.getPlayerName());
        }
    }

    /**
     * This methods uses the {@link IFromServerToClient#showCommand} method to show the commands still available to the
     * player on duty. In this case, the command {@link Commands#PLACEMENT} and the ones relative
     * to {@link ToolCard}s that imply a placement are removed from the list sent.
     * @param client client receiving the {@link Commands}.
     */
    private void showCommandsWithoutPlacement(IFromServerToClient client) throws BrokenConnectionException {
        client.showNotice("Hai già piazzato un dado in questo turno!\n" +
                "Puoi ancora usare una Carta Strumento che non implichi il piazzamento di un dado, o passare il turno.");
        List<Commands> modifiedCurrentPlayerList = new ArrayList<>(this.currentPlayerCommands);

        //Remove the placement command.
        modifiedCurrentPlayerList.remove(Commands.PLACEMENT);

        //Remove the commands of each tool card drafted that implies a placement.
        for(ToolCardSlot slot: super.getControllerMaster().getCommonBoard().getToolCardSlots()) {
            ToolCard toolCard = slot.getToolCard();
            if(toolCard.impliesPlacement()) {
                for (Commands command : toolCard.getEffectBuilder().getEffects()) {
                    modifiedCurrentPlayerList.remove(command);
                }
            }
        }

        //Show to the client the updated list of commands.
        client.showCommand(modifiedCurrentPlayerList);
    }

    /**
     * This methods uses the {@link IFromServerToClient#showCommand} method to show the commands still available to the
     * player on duty. In this case, all the commands relative to the {@link it.polimi.ingsw.model.cards.tool.ToolCard}s
     * are removed from the list sent.
     * @param client client receiving the {@link Commands}.
     */
    private void showCommandsWithoutTools(IFromServerToClient client) throws BrokenConnectionException {
        client.showNotice("Hai già usato una Carta Strumento in questo turno! Puoi ancora fare un piazzamento semplice," +
                " o passare il turno.");
        List<Commands> modifiedCurrentPlayerList = new ArrayList<>(this.currentPlayerCommands);

        //Remove tool cards commands
        for(ToolCardSlot slot: super.getControllerMaster().getCommonBoard().getToolCardSlots()) {
            ToolCard toolCard = slot.getToolCard();
            for(Commands command: toolCard.getEffectBuilder().getEffects()) {
                modifiedCurrentPlayerList.remove(command);
            }
        }

        //Show to the client the updated list of commands.
        client.showCommand(modifiedCurrentPlayerList);
    }

    /**
     * Lets the player on duty pass his turn, then starts the next one.
     * @param playerName player wanting to end his turn.
     */
    public void moveToNextTurn(String playerName) {
        this.handleIllegalStateRequests(playerName);

        GameState gameState = super.getControllerMaster().getGameState();
        gameState.getCurrentTurn().setPassed(true);
        this.endTurn();
    }

    /**
     * This method is a security measure: if a request which doesn't come from the player on duty arrives, it handles it
     * by showing the player sending it the commands he is allowed to use.
     * If the request arrive regularly, this method does nothing.
     * @param playerName name of the player sending the request.
     */
    private void handleIllegalStateRequests(String playerName) {
        IFromServerToClient client = super.getControllerMaster().getConnectedPlayers().get(playerName).getClient();
        if(!isRequestFromCurrentPlayer(playerName)) {
            try {
                client.showNotice("Non puoi effettuare questa mossa durante il turno degli altri!");
                client.showCommand(this.waitingPlayersCommands);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection with the client crashed while performing a " +
                        "default move", e);
                //todo super.getControllerMaster().suspendPlayer(playerName);
            }
        }
    }

    /**
     * This method checks if the right conditions to move to the next player are satisfied.
     * @return {@code true} if the end-turn conditions are satisfied, {@code false} otherwise.
     */
    /*private boolean isTurnOver(Turn turn) {
        //Checks if all possible moves have already been done.
        if (this.getControllerMaster().getGameState().isCurrentTurnOver())
            return true;

        List<ToolCardSlot> toolCardSlots = super.getControllerMaster().getCommonBoard().getToolCardSlots();
        int slotUsed = super.getControllerMaster().getGameState().getCurrentTurn().getToolSlotUsed();
        int playerTokens = super.getControllerMaster().getGameState().getCurrentPlayer().getFavorTokens();

        //Checks if the tool card didn't imply a placement and if the player has enough favor tokens to pay for it.
        if (this.getControllerMaster().getGameState().getTurnOrder().isDiePlaced())
            for (ToolCardSlot slot : toolCardSlots)
                if (slot.checkTokens(playerTokens) && !slot.checkImpliesPlacement())
                    return true;

        //Checks if the tool card implied a placement.
        if (this.getControllerMaster().getGameState().getCurrentTurn().isToolCardUsed())
            return (toolCardSlots.get(slotUsed).checkImpliesPlacement());

        //If none of the conditions ahead are verified.
        return false;
    }*/
}
