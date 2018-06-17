package it.polimi.ingsw.controller.managers;


import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.cards.tool.ToolCard;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.turn.GameState;
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
    private final List<Commands> currentPlayerCommands;

    /**
     * List of commands that the waiting players can perform.
     */
    private final List<Commands> waitingPlayersCommands;

    /**
     * Timer representing how much time each player has to complete his turn.
     */
    private Timer timer;

    /**
     * Says if the move done is legal or not. It's set by executeMove methods.
     */
    private boolean moveLegal = true;

    /**
     * Path of the file containing the amount of time to wait.
     */
    private static final String TIMER_FILE = "./src/main/java/it/polimi/ingsw/utils/config/turnTimer";

    /**
     * Timer to use in case the loading from file fails. Value is in milliseconds.
     */
    private static final long BACK_UP_TIMER = 90000;

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
                Commands.ROUND_TRACK, Commands.END_TURN, Commands.LOGOUT));

        this.waitingPlayersCommands = new ArrayList<>(Arrays.asList(Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS,
                Commands.ROUND_TRACK, Commands.LOGOUT));
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

            //Populates the dice draft pool and updates the view.
            super.getControllerMaster().getCommonBoard().getDraftPool().populateDiceDraftPool(players.size());
            this.broadcastNotification("\nÈ INIZIATO IL ROUND N° " + gameState.getActualRound() + "\n" +
                    "La riserva è stata aggiornata:");
            for(Player player: players) {
                IFromServerToClient client =
                        super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
                try {
                    client.setDraft(super.draftPoolConverter());
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Connection broken while updating draft pool at the start" +
                            "of a round", e);
                    //todo super.getControllerMaster().suspendPlayer(player.getPlayerName());
                }
            }

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
        GameState gameState = super.getControllerMaster().getGameState();
        try {
            //Check whether this is the first or the second turn of this player in this round.
            String turnNumber =
                    gameState.getCurrentPlayerTurnIndex() < (gameState.getTurnOrder().size()/2) ? "PRIMO" : "SECONDO";

            currentPlayerClient.showNotice("\nÈ IL TUO " + turnNumber + " TURNO!\n");
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
                    waitingPlayerClient.showNotice("\nÈ il turno di " + gameState.getCurrentPlayer().getPlayerName() + "\n");
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
            SagradaLogger.log(Level.CONFIG, "Timer successfully loaded from file. Its value is: " + timeOut/1000 + "s");
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
                    endTurn("\nIl tempo a tua disposizione è terminato. Sei stato sospeso per inattività.\n");
                }
                timer.cancel();
            }
        }, timeOut);
    }

    /**
     * Ends the turn, incrementing the index of the current player in the turn order list at {@link GameState}.
     * Then starts a new turn.
     * @param message
     */
    private void endTurn(String message) {
        GameState gameState = super.getControllerMaster().getGameState();
        IFromServerToClient currentPlayerClient =
                super.getControllerMaster().getConnectedPlayers().get(gameState.getCurrentPlayer().getPlayerName()).getClient();
        try {
            currentPlayerClient.showNotice(message);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken while ending the turn", e);
            //todo super.getControllerMaster().suspendPlayer(gameState.getCurrentPlayer().getPlayerName());
        }

        //If the round is not over, proceed to the next turn.
        if(gameState.getCurrentPlayerTurnIndex() < gameState.getTurnOrder().size() - 1) {
            gameState.incrementCurrentPlayerTurnIndex();
            this.startTurn(gameState.getCurrentTurn());
        }

        //If the round is over, start a new one, unless it was the last one. In that case, report that the match is over.
        else {
            this.endRound(gameState);
        }
    }

    private void endRound(GameState gameState) {
        gameState.resetCurrentPlayerTurnIndex();
        this.moveRemainingDiceFromDraftPoolToRoundTrack(gameState, super.getControllerMaster().getCommonBoard());
        if(gameState.getActualRound() < GameState.LAST_ROUND) {
            gameState.incrementActualRound();
        } else {
            gameState.setMatchOver(true);
        }
        startRound();
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
     * This method is used to perform a standard {@link DiePlacementMove}. It can handle weird situations in which a
     * player tries to perform moves not in his turn.
     * It also checks if a placement has already been done and in that case it shows to the player new suitable
     * {@link Commands}.
     * @param info object containing the information needed to update the model.
     * @param playerName name of the player trying to perform the move.
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
                List<Commands> filteredCommands = this.filterPlacement(this.currentPlayerCommands);
                try {
                    currentPlayerClient.showNotice("\nHai già effettuato un piazzamento in questo turno! Puoi ancora " +
                            "usare una Carta Strumento che non lo implichi, oppure passare.\n");
                    currentPlayerClient.showCommand(filteredCommands);
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
            this.endTurn("\nHai già effettuato tutte le operazioni possibili in un turno, " +
                    "passaggio al giocatore successivo...\n");
        }
    }

    /**
     * Shows the result of the default placement move to the player on duty and the waiting players.
     * @param currentPlayer player on duty.
     * @param setUpInfoUnit information used by the view to update
     *                               the {@link it.polimi.ingsw.model.die.containers.WindowPatternCard}.
     */
    public void showPlacementResult(Player currentPlayer, SetUpInformationUnit setUpInfoUnit) {
        GameState gameState = super.getControllerMaster().getGameState();
        gameState.getCurrentTurn().setDiePlaced(true);

        //Update the board of the player on duty.
        IFromServerToClient client = super.getControllerMaster().getConnectedPlayers().get(
                currentPlayer.getPlayerName()).getClient();
        try {
            client.addOnOwnWp(setUpInfoUnit);
            client.removeOnDraft(draftSetUpInformationUnit);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to update current player wp and draft pool", e);
            //todo super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName());
        }

        //Checks if the player has done everything he could. If he did, ends his turn; if not, shows him the commands.
        //The commands shown are relative to what the player can still do.
        if(!gameState.isCurrentTurnOver()) {
            try {
                this.filterPlacement(, );
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to show the player updated commands", e);
                //todo super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName());
            }
        } else {
            this.endTurn("Hai effettuato tutte le operazioni possibili in un turno, " +
                    "passaggio al giocatore successivo...");
        }

        //Update the board of the waiting players.
        List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
        for(Player p: waitingPlayers) {
            if(!p.isSamePlayerAs(currentPlayer)) {
                IFromServerToClient waitingPlayerClient =
                        super.getControllerMaster().getConnectedPlayers().get(p.getPlayerName()).getClient();
                try {
                    waitingPlayerClient.addOnOtherPlayerWp(currentPlayer.getPlayerName(), setUpInfoUnit);
                    waitingPlayerClient.removeOnDraft(draftSetUpInformationUnit);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Impossible to update waiting players wp and draft pool", e);
                    //todo super.getControllerMaster().suspendPlayer(p.getPlayerName);
                }
            }
        }
    }

    /**
     * This method filters the list of {@link Commands} in input, removing all the ones related to the placement,
     * including {@link ToolCard}s.
     * @param listToFilter list needing to be filtered.
     */
    private List<Commands> filterPlacement(List<Commands> listToFilter) {
        List<Commands> modifiedCurrentPlayerList = new ArrayList<>(listToFilter);

        //Remove the placement command.
        modifiedCurrentPlayerList.remove(Commands.PLACEMENT);

        //Remove the commands of each tool card drafted that implies a placement.
        for(ToolCardSlot slot: super.getControllerMaster().getCommonBoard().getToolCardSlots()) {
            if(slot.checkIfCardImpliesPlacement()) {
                modifiedCurrentPlayerList.remove(slot.getToolCard().getCommandName());
            }
        }

        return modifiedCurrentPlayerList;
    }

    /**
     * This method filters the list of {@link Commands} in input, removing all the ones related to {@link ToolCard}s.
     * @param listToFilter list needing to be filtered.
     */
    private List<Commands> filterTools(List<Commands> listToFilter) {
        List<Commands> modifiedCurrentPlayerList = new ArrayList<>(listToFilter);

        //Remove tool cards commands
        for(ToolCardSlot slot: super.getControllerMaster().getCommonBoard().getToolCardSlots()) {
            modifiedCurrentPlayerList.remove(slot.getToolCard().getCommandName());
        }

        return modifiedCurrentPlayerList;
    }

    /**
     * Lets the player on duty pass his turn, then starts the next one.
     * @param playerName player wanting to end his turn.
     */
    public void moveToNextTurn(String playerName) {
        this.handleIllegalStateRequests(playerName);

        GameState gameState = super.getControllerMaster().getGameState();
        gameState.getCurrentTurn().setPassed(true);
        this.endTurn("\nHai scelto di passare il turno.\n");
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
     * This method is used at the start of each round to move all remaining dice in the
     * {@link it.polimi.ingsw.model.die.containers.DiceDraftPool} to the space on the
     * {@link it.polimi.ingsw.model.die.containers.RoundTrack} corresponding to the current round.
     */
    private void moveRemainingDiceFromDraftPoolToRoundTrack(GameState gameState, CommonBoard board) {
        if(!board.getDraftPool().getAvailableDice().isEmpty()) {
            board.getRoundTrack().setRoundToBeUpdated(gameState.getActualRound() - 1);
            int actualDraftSize = board.getDraftPool().getAvailableDice().size();
            for(int i = 0; i < actualDraftSize; i++) {
                board.getRoundTrack().addDie(board.getDraftPool().removeDie(0));
            }
        }
    }

    public boolean isMoveLegal() {
        return moveLegal;
    }

    public void setMoveLegal(boolean moveLegal) {
        this.moveLegal = moveLegal;
    }
}
